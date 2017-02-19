/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.messaging.exceptions.MessagingException;
import org.wso2.carbon.messaging.exceptions.NelException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Data carrier between the components.
 */
public abstract class CarbonMessage {

    private static final Logger LOG = LoggerFactory.getLogger(CarbonMessage.class);

    protected Headers headers = new Headers();
    protected Map<String, Object> properties = new HashMap<>();
    protected BlockingQueue messageBody = new LinkedBlockingQueue<>();
    protected Stack<FaultHandler> faultHandlerStack = new Stack<>();
    protected MessageDataSource messageDataSource;
    /**
     * @deprecated This field will be replace by {@link #messagingException}
     */
    @Deprecated
    protected NelException nelException = null;

    /**
     * Exception related to fault CarbonMessage.
     */
    protected MessagingException messagingException = null;

    protected ByteBufferInputStream byteBufferInputStream;

    private ByteBufferOutputStream byteBufferOutputStream;

    protected Lock lock = new ReentrantLock();

    protected boolean bufferContent = true;

    protected AtomicBoolean alreadyRead = new AtomicBoolean(false);

    private AtomicBoolean endOfMsgAdded = new AtomicBoolean(false);

    private Writer writer;
    private boolean isMessageBodyAdded;

    public CarbonMessage() {
    }

    /**
     * This enable you to avoid filling content in to internal buffer.
     * Use this constructor when creating response message and need to write content and
     *
     * @param buffercontent enables direct writing to channel if true else buffer content in message queue
     */
    public CarbonMessage(Boolean buffercontent) {
        this.bufferContent = buffercontent;
    }

    public void setBufferContent(boolean bufferContent) {
        if (isMessageBodyAdded) {
            throw new IllegalStateException(
                    "CarbonMessage#setBufferContent cannot " + "be called after adding message body");
        }
        this.bufferContent = bufferContent;
    }

    public boolean isEndOfMsgAdded() {
        return endOfMsgAdded.get();
    }

    public boolean isEmpty() {
        return messageBody.isEmpty();
    }

    public ByteBuffer getMessageBody() {
        try {
            return (ByteBuffer) messageBody.take();
        } catch (InterruptedException e) {
            LOG.error("Error while retrieving chunk from queue.", e);
            return null;
        }

    }

    /**
     * Calling this method will be blocked until all the message content is received.
     *
     * @return Full message body as list of {@link ByteBuffer}
     */
    public List<ByteBuffer> getFullMessageBody() {
        List<ByteBuffer> byteBufferList = new ArrayList<>();

        while (true) {
            try {
                if (endOfMsgAdded.get() && messageBody.isEmpty()) {
                    break;
                }
                byteBufferList.add((ByteBuffer) messageBody.take());
            } catch (InterruptedException e) {
                LOG.error("Error while getting full message body", e);
            }
        }
        return byteBufferList;
    }

    public void addMessageBody(ByteBuffer msgBody) {
        isMessageBodyAdded = true;
        if (bufferContent) {
            messageBody.add(msgBody);
        } else {
            if (writer != null) {
                writer.write(msgBody);
            } else {
                LOG.error("Cannot write content no registered writer found");
            }
        }
    }

    /**
     * Method to be used for resources clean up after using Carbon Messaging.
     */
    public void release() {

    }

    public Headers getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void setHeader(String key, String value) {
        headers.set(key, value);
    }

    public void setHeaders(Map<String, String> headerMap) {
        headers.set(headerMap);
    }

    public void setHeaders(List<Header> headerList) {
        headers.set(headerList);
    }

    public Object getProperty(String key) {
        if (properties != null) {
            return properties.get(key);
        } else {
            return null;
        }
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    public void removeHeader(String key) {
        headers.remove(key);
    }

    public void removeProperty(String key) {
        properties.remove(key);
    }

    public Stack<FaultHandler> getFaultHandlerStack() {
        return faultHandlerStack;
    }

    public void setFaultHandlerStack(Stack<FaultHandler> faultHandlerStack) {
        this.faultHandlerStack = faultHandlerStack;
    }

    public Lock getLock() {
        return lock;
    }

    public int getFullMessageLength() {
        List<ByteBuffer> fullMessageBody = getFullMessageBody();
        int size = (int) fullMessageBody.stream().mapToInt(byteBuffer -> byteBuffer.limit()).sum();
        fullMessageBody.forEach(byteBuffer -> addMessageBody(byteBuffer));
        return size;
    }

    public List<ByteBuffer> getCopyOfFullMessageBody() {
        List<ByteBuffer> fullMessageBody = getFullMessageBody();
        List<ByteBuffer> newCopy = fullMessageBody.stream().map(byteBuffer -> MessageUtil.deepCopy(byteBuffer))
                .collect(Collectors.toList());
        fullMessageBody.forEach(byteBuffer -> addMessageBody(byteBuffer));
        markMessageEnd();
        return newCopy;
    }

    /**
     * This method is used to mark the end of the message when cloning the content.
     */
    public void markMessageEnd() {
    }

    public void setEndOfMsgAdded(boolean endOfMsgAdded) {
        this.endOfMsgAdded.compareAndSet(false, endOfMsgAdded);
        if (byteBufferOutputStream != null) {
            try {
                this.byteBufferOutputStream.flush();
            } catch (IOException e) {
                LOG.error("Exception occured while flushing the buffer", e);
                byteBufferOutputStream.close();
            }
        }
        ;
        if (writer != null) {
            writer.writeLastContent(this);
        }
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public boolean isBufferContent() {
        return bufferContent;
    }

    public MessageDataSource getMessageDataSource() {
        return messageDataSource;
    }

    public void setMessageDataSource(MessageDataSource messageDataSource) {
        this.messageDataSource = messageDataSource;
    }

    public boolean isAlreadyRead() {
        return alreadyRead.get();
    }

    public void setAlreadyRead(boolean alreadyRead) {
        this.alreadyRead.set(alreadyRead);
    }

    /**
     * This is a blocking call and provides full message as inputStream
     * removes original content from queue.
     *
     * @return InputStream Instance.
     */
    public InputStream getInputStream() {
        if (byteBufferInputStream == null) {
            byteBufferInputStream = new ByteBufferInputStream();
        }
        return byteBufferInputStream;
    }

    /**
     * This provide access to write byte stream in to message content Queue as
     * Stream
     *
     * @return OutputStream Instance.
     */
    public OutputStream getOutputStream() {
        if (byteBufferOutputStream == null) {
            byteBufferOutputStream = new ByteBufferOutputStream();
        }
        return byteBufferOutputStream;
    }

    /**
     * A class which represents the InputStream of the ByteBuffers
     * No need to worry about thread safety of this class this is called only once by
     * for a message instance from one thread.
     */
    protected class ByteBufferInputStream extends InputStream {

        private int count;
        private boolean chunkFinished = true;
        private int limit;
        private ByteBuffer byteBuffer;

        @Override
        public int read() throws IOException {
            setAlreadyRead(true); // TODO: No need to set this again and again
            if (isEndOfMsgAdded() && isEmpty() && chunkFinished) {
                return -1;
            } else if (chunkFinished) {
                byteBuffer = getMessageBody();
                count = 0;
                limit = byteBuffer.limit();
                if (limit == 0) {
                    return -1;
                }
                chunkFinished = false;
            }
            count++;
            if (count == limit) {
                chunkFinished = true;
            }
            return byteBuffer.get() & 0xff;
        }
    }

    /**
     * A class which write byteStream into ByteBuffers and add those
     * ByteBuffers to Content Queue.
     * No need to worry about thread safety of this class this is called only once by
     * one thread at particular time.
     */
    protected class ByteBufferOutputStream extends OutputStream {

        private ByteBuffer buffer;

        @Override
        public void write(int b) throws IOException {
            if (buffer == null) {
                buffer = BufferFactory.getInstance().getBuffer();
            }
            if (buffer.hasRemaining()) {
                buffer.put((byte) b);
            } else {
                buffer.flip();
                addMessageBody(buffer);
                buffer = BufferFactory.getInstance().getBuffer();
                buffer.put((byte) b);
            }

        }

        @Override
        public void flush() throws IOException {
            if (buffer != null && buffer.position() > 0) {
                buffer.flip();
                addMessageBody(buffer);
                buffer = BufferFactory.getInstance().getBuffer();
            }
        }

        @Override
        public void close() {
            try {
                super.close();
            } catch (IOException e) {
                LOG.error("Error while closing output stream but underlying resources are reset", e);
            } finally {
                byteBufferOutputStream = null;
                buffer = null;
            }

        }
    }

    /**
     * Get NelException
     *
     * @return NelException instance.
     * @deprecated Get NelException method will be replaced by {@link #getMessagingException()} method.
     */
    @Deprecated
    public NelException getNelException() {
        return nelException;
    }

    /**
     * Set NelException.
     *
     * @param nelException NelException instance related to faulty CarbonMessage.
     * @deprecated Set NelException will be replaced by
     * {@link #setMessagingException(MessagingException carbonMessageException)} method.
     */
    @Deprecated
    public void setNelException(NelException nelException) {
        this.nelException = nelException;
    }

    /**
     * Get CarbonMessageException
     *
     * @return CarbonMessageException instance related to faulty CarbonMessage.
     */
    public MessagingException getMessagingException() {
        return messagingException;
    }

    /**
     * Set CarbonMessageException.
     *
     * @param messagingException exception related to faulty CarbonMessage.
     */
    public void setMessagingException(MessagingException messagingException) {
        this.messagingException = messagingException;
    }

    public boolean isFaulty() {
        // TODO: Remove {@link #nelException} reference.
        return (this.messagingException != null || this.nelException != null);
    }
}

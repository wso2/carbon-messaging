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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Data carrier between the components.
 */
public abstract class CarbonMessage {

    private static final Logger LOG = LoggerFactory.getLogger(CarbonMessage.class);

    protected Map<String, String> headers = new ConcurrentHashMap<>();
    protected Map<String, Object> properties = new ConcurrentHashMap<>();
    protected BlockingQueue<ByteBuffer> messageBody = new LinkedBlockingQueue<>();
    protected Stack<FaultHandler> faultHandlerStack = new Stack<>();

    protected Lock lock = new ReentrantLock();

    protected boolean bufferContent = true;

    private boolean endOfMsgAdded = false;

    private Writer writer;

    public CarbonMessage() {
    }

    /**
     * This enable you to avoid filling content in to internal buffer
     * Use this constructor when creating response message and need to write content and
     *
     * @param buffercontent
     */
    public CarbonMessage(Boolean buffercontent) {
        this.bufferContent = buffercontent;
    }

    public boolean isEndOfMsgAdded() {
        return endOfMsgAdded;
    }

    public boolean isEmpty() {
        return messageBody.isEmpty();
    }

    public ByteBuffer getMessageBody() {
        try {
            return messageBody.take();
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
                if (endOfMsgAdded && messageBody.isEmpty()) {
                    break;
                }
                byteBufferList.add(messageBody.take());
            } catch (InterruptedException e) {
                LOG.error("Error while getting full message body", e);
            }
        }
        return byteBufferList;
    }

    public void addMessageBody(ByteBuffer msgBody) {
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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setHeaders(Map<String, String> headerMap) {
        headerMap.forEach(headers::put);
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
        int size = 0;
        for (ByteBuffer byteBuffer : fullMessageBody) {
            messageBody.add(byteBuffer);
            size += byteBuffer.limit();
        }
        return size;
    }

    public void setEndOfMsgAdded(boolean endOfMsgAdded) {
        this.endOfMsgAdded = endOfMsgAdded;
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
}

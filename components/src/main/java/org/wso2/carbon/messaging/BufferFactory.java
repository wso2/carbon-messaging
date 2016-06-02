/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A class which is responsible for create ByteBuffers.
 */
public class BufferFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BufferFactory.class);
    private static BufferFactory preConfiguredBufferFactory;
    private static BufferFactory defaultBufferFactory = new BufferFactory();

    private Queue<ByteBuffer> byteBufferQueue = new ConcurrentLinkedQueue<>();

    private int bufferSize = 1024 * 8;

    public BufferFactory(int bufferSize) {
        LOGGER.debug("Creating BufferFactory with BufferSize " + bufferSize);
        this.bufferSize = bufferSize;

    }

    private BufferFactory() {
        LOGGER.debug("Creating BufferFactory with default BufferSize " + this.bufferSize);

    }

    public ByteBuffer getBuffer() {

        ByteBuffer byteBuffer = null;
        if (byteBufferQueue.isEmpty()) {
            byteBuffer = ByteBuffer.allocate(bufferSize);
        } else {
            byteBuffer = byteBufferQueue.poll();
        }
        return byteBuffer;
    }

    public void release(ByteBuffer buffer) {
        buffer.clear();
        byteBufferQueue.add(buffer);
    }

    public static synchronized BufferFactory createInstance(int bufferSize) {
        return preConfiguredBufferFactory = new BufferFactory(bufferSize);
    }

    public static BufferFactory getInstance() {
        if (preConfiguredBufferFactory == null) {
            return defaultBufferFactory;
        }
        return preConfiguredBufferFactory;

    }

}

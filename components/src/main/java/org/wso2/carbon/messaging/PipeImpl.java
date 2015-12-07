/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package org.wso2.carbon.messaging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A class that contains the content of request.
 */
public class PipeImpl implements Pipe {

    private static final Logger LOG = LoggerFactory.getLogger(PipeImpl.class);

    private BlockingQueue<ContentChunk> contentQueue;


    public PipeImpl(int blockingQueueSize) {
        this.contentQueue = new LinkedBlockingQueue<>(blockingQueueSize);
    }


    public ContentChunk getContent() {
        try {
            return contentQueue.take();
        } catch (InterruptedException e) {
            LOG.error("Error while retrieving chunk from queue.", e);
            return null;
        }
    }

    public void addContentChunk(ContentChunk contentChunk) {
        contentQueue.add(contentChunk);
    }


}

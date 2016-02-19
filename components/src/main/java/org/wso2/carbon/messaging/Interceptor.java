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

import java.util.HashMap;
import java.util.Map;

/**
 * The class that is responsible for engaging all the interceptors.
 */
public class Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(Interceptor.class);
    private Map<String, MessagingHandler> handlers = new HashMap<>();

    public boolean sourceConnection(String metadata, State state) {
        try {
            handlers.forEach((k, v) -> v.sourceConnection(metadata, state));
        } catch (Exception e) {
            LOG.error("Error while executing handler at SourceConnection with " + state, e);
        }
        return true;
    }

    public boolean sourceRequest(CarbonMessage carbonMessage, State state) {
        try {
            handlers.forEach((k, v) -> v.sourceRequest(carbonMessage, state));
        } catch (Exception e) {
            LOG.error("Error while executing handler at SourceRequest with " + state, e);
        }
        return true;
    }

    public boolean sourceResponse(CarbonMessage carbonMessage, State state) {
        try {
            handlers.forEach((k, v) -> v.sourceResponse(carbonMessage, state));
        } catch (Exception e) {
            LOG.error("Error while executing handler at SourceResponse with " + state, e);
        }
        return true;
    }

    public boolean targetConnection(String metadata, State state) {
        try {
            handlers.forEach((k, v) -> v.targetConnection(metadata, state));
        } catch (Exception e) {
            LOG.error("Error while executing handler at TargetConnection with " + state, e);
        }
        return true;
    }

    public boolean targetRequest(CarbonMessage carbonMessage, State state) {
        try {
            handlers.forEach((k, v) -> v.targetRequest(carbonMessage, state));
        } catch (Exception e) {
            LOG.error("Error while executing handler at TargetRequest with " + state, e);
        }
        return true;
    }

    public boolean targetResponse(CarbonMessage carbonMessage, State state) {
        try {
            handlers.forEach((k, v) -> v.targetResponse(carbonMessage, state));
        } catch (Exception e) {
            LOG.error("Error while executing handler at TargetResponse with " + state, e);
        }
        return true;
    }

    public void addHandler(MessagingHandler messagingHandler) {
        handlers.put(messagingHandler.handlerName(), messagingHandler);
        LOG.info("A new handler named " + messagingHandler.handlerName() + " is added to the Interceptor");
    }

    public void removeHandler(MessagingHandler messagingHandler) {
        handlers.remove(messagingHandler.handlerName());
        LOG.info("A new handler named " + messagingHandler.handlerName() + " is removed from the Interceptor");
    }
}

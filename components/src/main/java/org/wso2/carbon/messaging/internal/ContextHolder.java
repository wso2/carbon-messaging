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

package org.wso2.carbon.messaging.internal;

import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.TransportListener;
import org.wso2.carbon.messaging.TransportSender;

import java.util.HashMap;
import java.util.Map;

/**
 * ContextHolder wires the Engine, Transport Sender and Transport Listener
 */
public class ContextHolder {

    public ContextHolder() {
    }

    private static ContextHolder instance = new ContextHolder();
    private Map<String, CarbonMessageProcessor> engines = new HashMap<>();
    private Map<String, TransportSender> transportSenders = new HashMap<>();
    private Map<String, TransportListener> transportListeners = new HashMap<>();


    public static ContextHolder getInstance() {
        return instance;
    }

    public void addEngine(CarbonMessageProcessor carbonMessageProcessor) {
        if (!transportSenders.isEmpty()) {
            Map.Entry<String, TransportSender> senderEntry = transportSenders.entrySet().iterator().next();
            carbonMessageProcessor.setTransportSender(senderEntry.getValue());
        }

        if (!transportListeners.isEmpty()) {
            transportListeners.forEach((k, v) -> v.setEngine(carbonMessageProcessor));
        }

        engines.put(carbonMessageProcessor.getId(), carbonMessageProcessor);
    }

    public void removeEngine(CarbonMessageProcessor carbonMessageProcessor) {
        engines.remove(carbonMessageProcessor.getId());
    }

    public void addTransportSender(TransportSender transportSender) {
        if (!engines.isEmpty()) {
            Map.Entry<String, CarbonMessageProcessor> engineEntry = engines.entrySet().iterator().next();
            engineEntry.getValue().setTransportSender(transportSender);
        }
        transportSenders.put(transportSender.getId(), transportSender);
    }

    public void removeTransportSender(TransportSender transportSender) {
        transportSenders.remove(transportSender.getId());
    }

    public void addTransportListener(TransportListener transportListener) {
        if (!engines.isEmpty()) {
            Map.Entry<String, CarbonMessageProcessor> engineEntry = engines.entrySet().iterator().next();
            transportListener.setEngine(engineEntry.getValue());
        }
        transportListeners.put(transportListener.getId(), transportListener);
    }

    public void removeTransportListener(TransportListener transportListener) {
        transportListeners.remove(transportListener.getId());
    }
}

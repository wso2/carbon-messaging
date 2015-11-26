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

import org.osgi.framework.BundleContext;
import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.TransportListener;
import org.wso2.carbon.messaging.TransportSender;

import java.util.HashMap;
import java.util.Map;

public class ContextHolder {

    public ContextHolder() {
    }

    private static ContextHolder instance = new ContextHolder();
    private BundleContext bundleContext;
    private Map<String, CarbonMessageProcessor> engines = new HashMap<>();
    private Map<String, TransportSender> transportSenders = new HashMap<>();
    private Map<String, TransportListener> transportListeners = new HashMap<>();

    private String listenerName;

    public static ContextHolder getInstance() {
        return instance;
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }


    public void addEngine(CarbonMessageProcessor carbonMessageProcessor) {
        if(!transportSenders.isEmpty()) {
            carbonMessageProcessor.setTransportSender(transportSenders.get("modifyInterfaceToGetName"));
        }

        if(!transportListeners.isEmpty()) {
            transportListeners.get("modifyInterfaceToGetName").setEngine(carbonMessageProcessor);
        }

        engines.put("modifyInterfaceToGetName", carbonMessageProcessor);
    }

    public void removeEngine(CarbonMessageProcessor carbonMessageProcessor) {
        engines.remove("modifyInterfaceToGetName");
    }

    public void addTransportSender(TransportSender transportSender) {
        if(!engines.isEmpty()) {
            engines.get("modifyInterfaceToGetName").setTransportSender(transportSender);
        }
        transportSenders.put("modifyInterfaceToGetName", transportSender);
    }

    public void removeTransportSender(TransportSender transportSender) {
        transportSenders.remove("modifyInterfaceToGetName");
    }

    public void addTransportListener(TransportListener transportListener) {
        if(!engines.isEmpty()) {
            transportListener.setEngine(engines.get("modifyInterfaceToGetName"));
        }
        listenerName = transportListener.getId();
        transportListeners.put(transportListener.getId(), transportListener);
    }

    public void removeTransportListener(TransportListener transportListener) {
        transportListeners.remove(transportListener.getId());
    }
}

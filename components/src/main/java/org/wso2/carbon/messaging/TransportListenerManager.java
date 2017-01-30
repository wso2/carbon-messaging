/*
 *  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wso2.carbon.messaging;


import java.lang.management.ManagementPermission;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for managing the transport listeners available.
 */
public class TransportListenerManager implements TransportManagerMBean {

    private Map<String, TransportListener> transports = new HashMap<>();

    public void registerTransport(TransportListener transport) {
        transports.put(transport.getId(), transport);
    }

    public void unregisterTransport(TransportListener transport) {
        transports.remove(transport.getId());
    }

    public void startTransport(String transportId) {
        TransportListener transport = transports.get(transportId);
        if (transport == null) {
            throw new IllegalArgumentException(transportId + " not found");
        }
        checkSecurity();
        transport.startTransport();
    }

    public void stopTransport(String transportId) {
        TransportListener transport = transports.get(transportId);
        if (transport == null) {
            throw new IllegalArgumentException(transportId + " not found");
        }
        checkSecurity();
        transport.stopTransport();
    }

    public void startTransports() {
        checkSecurity();
        transports.values()
                .forEach(TransportListener::startTransport);
    }

    public void stopTransports() {
        checkSecurity();
        transports.values()
                .forEach(TransportListener::stopTransport);
    }

    public void beginMaintenance() {
        checkSecurity();
        transports.values()
                .forEach(TransportListener::beginTransportMaintenance);
    }

    public void endMaintenance() {
        checkSecurity();
        transports.values()
                .forEach(TransportListener::endTransportMaintenance);
    }

    public Map<String, TransportListener> getTransports() {
        checkSecurity();
        return transports;
    }

    public TransportListener getTransport(String transportId) {
        return transports.get(transportId);
    }

    /**
     * When the java security manager is enabled, the {@code checkSecurity} method can be used to protect/prevent
     * methods being executed by unsigned code.
     */
    public void checkSecurity() {
        SecurityManager secMan = System.getSecurityManager();
        if (secMan != null) {
            secMan.checkPermission(new ManagementPermission("control"));
        }
    }
}

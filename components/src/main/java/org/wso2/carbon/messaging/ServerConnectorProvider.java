/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.messaging;

import java.util.List;
import java.util.Map;

/**
 * This class represents the provider for server connectors. This should be used when a new connector instance
 * need to be created after server is started. There should be a server connector provider for each transport
 * protocol that server connectors are written. This will create and provide new instances of server connector
 * when the id is given. Also this will initialize the default server connectors that should be initialized
 * during application startup.
 */
public abstract class ServerConnectorProvider {
    /**
     * Protocol representing this server connector provider.
     */
    protected String protocol;

    public ServerConnectorProvider(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Returns the transport protocol associated with this provider.
     * @return transport protocol.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Initialize the default set of server connectors using the given configuration and return them.
     * @return list of initialized connectors.
     */
    public abstract List<ServerConnector> initializeConnectors();

    /**
     * Returns an instance of the {@link ServerConnector} using the given id.
     * @param id id used to create the server connector instance.
     * @param properties properties required for the {@link ServerConnector}
     * @return newly created server connector instance.
     */
    public abstract ServerConnector createConnector(String id, Map<String, String> properties);
}

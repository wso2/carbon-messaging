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

import org.wso2.carbon.messaging.exceptions.ClientConnectorException;

import java.util.Map;

/**
 * Message sending interface that should be implemented for a given transport protocol which will be used with
 * sending messages to a desired endpoint.
 */
public interface ClientConnector {

    /**
     * Initialize the connection. If initializing a connection is needed this can be used.
     *
     * @param cMsg carbon message used to initialize th connection.
     * @param callback carbon callback used to get responds if needed.
     * @param properties properties which needs to initialize the  connection.
     * @return Object which can be retrieved after initializing the connection.
     * @throws ClientConnectorException on error while trying to initializing the connection.
     */
    Object init(CarbonMessage cMsg, CarbonCallback callback, Map<String, Object> properties)
            throws ClientConnectorException;

    /**
     * Message sending logic to send message to a backend endpoint.
     *
     * @param msg the carbon message used with sending the a message to backend.
     * @param callback carbon callback used with responding any error.
     * @return return true if the sending was successful, false otherwise.
     * @throws ClientConnectorException on error while trying to send message to backend.
     */
    boolean send(CarbonMessage msg, CarbonCallback callback) throws ClientConnectorException;

    /**
     * Message sending logic to send message to a backend endpoint. Additionally, this method accepts a map of
     * parameters that is used as data to construct the message to be send.
     *
     * @param msg the carbon message used with sending the a message to backend.
     * @param callback carbon callback used with responding any error.
     * @param parameters data passed from application level to be used with creating the message.
     * @return return true if the sending was successful, false otherwise.
     * @throws ClientConnectorException on error while trying to send message to backend.
     */
    boolean send(CarbonMessage msg, CarbonCallback callback, Map<String, String> parameters)
            throws ClientConnectorException;

    /**
     * Transport protocol associated with this client connector instance.
     * @return string value of the transport protocol.
     */
    String getProtocol();

    /**
     * Set message processor reference to the ClientConnector.
     *
     * Reference to the engine Message Processor might be required for some of the client connectors to
     * get back to the engine with the response.
     *
     * @param messageProcessor CarbonMessageProcessor
     */
    void setMessageProcessor(CarbonMessageProcessor messageProcessor);
}

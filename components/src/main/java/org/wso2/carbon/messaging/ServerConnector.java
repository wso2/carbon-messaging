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

import org.wso2.carbon.messaging.exceptions.ServerConnectorException;

import java.util.Map;

/**
 * This class represents a server connector. When adding a new server connector to handle inbound requests over
 * different transport protocols such as http, jms, file, etc., this class needs to be extended, and the relevant
 * methods needs to be implemented. A server connector initialize ans destroy methods are called during application
 * startup and shutdown respectively. Similarly, the start-maintenance and end-maintenance methods will be invoked,
 * when the application runtime needs to put in the maintenance mode and then resume accepting requests.
 */
public abstract class ServerConnector {
    /**
     * Unique ID representing a server connector.
     */
    protected String id;

    protected State state = State.UNINITIALIZED;

    protected Map<String, String> properties;

    public ServerConnector(String id, Map<String, String> properties) {
        this.id = id;
        this.properties = properties;
    }

    /**
     * Get the properties of the connector.
     * @return properties.
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Set the message processor to be used with this connector for dispatching.
     * @param messageProcessor message processor instance
     */
    public abstract void setMessageProcessor(CarbonMessageProcessor messageProcessor);

    /**
     * Returns the id of the connector.
     * @return connector id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the current life cycle state of the connector.
     * @return lifecycle state
     */
    public State getState() {
        return state;
    }

    public void initConnector() throws ServerConnectorException {
        if (state.equals(State.UNINITIALIZED) || state.equals(State.IN_MAINTENANCE)) {
            init();
            state = State.INITIALIZED;
        } else {
            throw new IllegalStateException("Cannot initialize connector " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the connector init process.
     */
    protected abstract void init() throws ServerConnectorException;

    public void destroyConnector() throws ServerConnectorException {
        if (state.equals(State.INITIALIZED)) {
            destroy();
            state = State.UNINITIALIZED;
        } else {
            throw new IllegalStateException("Cannot destroy connector " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the connector destroy process.
     */
    protected abstract void destroy() throws ServerConnectorException;

    public void beginConnectorMaintenance() throws ServerConnectorException {
        if (state.equals(State.INITIALIZED)) {
            beginMaintenance();
            state = State.IN_MAINTENANCE;
        } else {
            throw new IllegalStateException("Cannot put connector " + id +
                    " into maintenance. Current state: " + state);
        }
    }

    /**
     * Implementation of the connector begin maintenance process.
     */
    protected abstract void beginMaintenance() throws ServerConnectorException;

    public void endConnectorMaintenance() throws ServerConnectorException {
        if (state.equals(State.IN_MAINTENANCE)) {
            endMaintenance();
            state = State.INITIALIZED;
        } else {
            throw new IllegalStateException("Cannot end maintenance of connector " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the connector end maintenance process.
     */
    protected abstract void endMaintenance() throws ServerConnectorException;

    /**
     * Implementation of the connector start method. Different connectors will use various approach to start the
     * connector (http will start bind on an interface, jms will start subscribe to a topic/queue).
     *
     * @throws ServerConnectorException when an error occurs during starting the connector.
     */
    public abstract void start() throws ServerConnectorException;

    /**
     * Implementation of the connector stop method. Different connectors will use various approach to stop the
     * connector (http will stop bind from an interface, jms will un-subscribe from a topic/queue)
     *
     * @throws ServerConnectorException when an error occurs during stoping the connector.
     */
    public abstract void stop() throws ServerConnectorException;

    /**
     * Enum to holds the state of connector.
     */
    public enum State {
        UNINITIALIZED, INITIALIZED, IN_MAINTENANCE;

        @Override
        public String toString() {
            return name();
        }
    }
}

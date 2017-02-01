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

/**
 * This class represents a server connector. When adding a new server connector, this class needs to be
 * extended, implement the start, stop, beginMaintenance, endMaintenance methods.
 */
public abstract class ServerConnector {
    /**
     * Unique ID representing a server connector.
     */
    protected String id;

    protected State state = State.UNINITIALIZED;

    public ServerConnector(String id) {
        this.id = id;
    }

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

    void startConnector() {
        if (state.equals(State.UNINITIALIZED) || state.equals(State.IN_MAINTENANCE) || state.equals(State.STOPPED)) {
            start();
            state = State.STARTED;
        } else {
            throw new IllegalStateException("Cannot start connector " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the connector start process.
     */
    protected abstract void start();

    void stopConnector() {
        if (state.equals(State.STARTED)) {
            stop();
            state = State.STOPPED;
        } else {
            throw new IllegalStateException("Cannot stop connector " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the connector stop process.
     */
    protected abstract void stop();

    void beginConnectorMaintenance() {
        if (state.equals(State.STARTED)) {
            beginMaintenance();
            state = State.IN_MAINTENANCE;
        } else {
            throw new IllegalStateException("Cannot put connector " + id +
                    " into maintenance. Current state: " + state);
        }
    }

    /**
     * Implementation of the connector start maintenance process.
     */
    protected abstract void beginMaintenance();

    void endConnectorMaintenance() {
        if (state.equals(State.IN_MAINTENANCE)) {
            endMaintenance();
            state = State.STARTED;
        } else {
            throw new IllegalStateException("Cannot end maintenance of connector " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the connector end maintenance process.
     */
    protected abstract void endMaintenance();

    /**
     * Enum to holds the state of connector.
     */
    public enum State {
        UNINITIALIZED, STARTED, STOPPED, IN_MAINTENANCE;

        @Override
        public String toString() {
            return name();
        }
    }
}

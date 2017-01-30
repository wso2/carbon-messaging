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

/**
 * Abstract class for Transport listener.
 */
public abstract class TransportListener {
    /**
     * Unique ID representing a transport.
     */
    protected String id;

    protected TransportListener.State state = TransportListener.State.UNINITIALIZED;

    public TransportListener(String id) {
        this.id = id;
    }

    public TransportListener() {}

    public void setId(String id) {
        this.id = id;
    }
    /**
     * Sets the {@code CarbonMessageProcessor} that will be used for this listener instance.
     * @param messageProcessor the messageProcessor instance to be used
     */
    public abstract void setMessageProcessor(CarbonMessageProcessor messageProcessor);

    /**
     * Bind on given interface
     * @param interfaceId has port , host and all interface parameters
     * @return true if bind success, false otherwise.
     */
    public abstract boolean bind(String  interfaceId);

    /**
     * Stop Listening in given interface if already listening (Un binding interface)
     * @param interfaceId has port , host and all interface parameters
     * @return true if unbind success, false otherwise.
     */
    public abstract boolean unBind(String  interfaceId);

    /**
     * Returns the string value of the transport protocol (eg: "http", "jms", etc. ) this listener is bound to.
     * @return transport protocol
     */
    public abstract String getProtocol();

    /**
     * Returns the ID for this transport listener
     * @return transport listener id
     */
    public String getId() {
        return id;
    }

    public TransportListener.State getState() {
        return state;
    }

    void startTransport() {
        if (state.equals(TransportListener.State.UNINITIALIZED) || state.equals(TransportListener.State.IN_MAINTENANCE)
                || state.equals(TransportListener.State.STOPPED)) {
            start();
            state = TransportListener.State.STARTED;
        } else {
            throw new IllegalStateException("Cannot start transport " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the transport start process.
     */
    protected abstract void start();

    void stopTransport() {
        if (state.equals(TransportListener.State.STARTED)) {
            stop();
            state = TransportListener.State.STOPPED;
        } else {
            throw new IllegalStateException("Cannot stop transport " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the transport stop process.
     */
    protected abstract void stop();

    void beginTransportMaintenance() {
        if (state.equals(TransportListener.State.STARTED)) {
            beginMaintenance();
            state = TransportListener.State.IN_MAINTENANCE;
        } else {
            throw new IllegalStateException("Cannot put transport " + id +
                    " into maintenance. Current state: " + state);
        }
    }

    /**
     * Implementation of the transport start maintenance process.
     */
    protected abstract void beginMaintenance();

    void endTransportMaintenance() {
        if (state.equals(TransportListener.State.IN_MAINTENANCE)) {
            endMaintenance();
            state = TransportListener.State.STARTED;
        } else {
            throw new IllegalStateException("Cannot end maintenance of transport " + id + ". Current state: " + state);
        }
    }

    /**
     * Implementation of the transport end maintenance process.
     */
    protected abstract void endMaintenance();

    /**
     * Enum to holds the state of Transport.
     */
    public enum State {
        UNINITIALIZED, STARTED, STOPPED, IN_MAINTENANCE;

        @Override
        public String toString() {
            return name();
        }
    }
}

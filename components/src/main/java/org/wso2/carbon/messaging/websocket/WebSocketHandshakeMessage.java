/*
 *   Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.wso2.carbon.messaging.websocket;

/**
 * WebSocket handshake should be done after checking whether there is a endpoint exists or not.
 * So this abstract class should be implemented and sent to the application level to do necessary checks and
 * handshake appropriately.
 */
public abstract class WebSocketHandshakeMessage extends WebSocketMessage {

    public WebSocketHandshakeMessage(WebSocketResponder webSocketResponder) {
        super(webSocketResponder); //WebSocketMessage initialization is not necessary for this class
    }

    /**
     * If all necessities are satisfied call this method to handshake.
     * @return true when the handshake is completed properly.
     */
    public abstract boolean handshake();

    /**
     * Call this unless all necessities are completed to do the handshake.
     */
    public abstract void cancel();
}

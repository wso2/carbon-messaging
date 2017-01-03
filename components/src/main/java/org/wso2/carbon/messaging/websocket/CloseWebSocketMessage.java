/*
 *   Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 * {@link WebSocketMessage} type for WebSocket Close Message.
 * @since 1.0.0
 */
public class CloseWebSocketMessage extends WebSocketMessage {

    private String reasonText; //Reason saying why the connection is closed
    private int statusCode; //Status code of the connection close

    /**
     * @param statusCode Status code of reason to close.
     * @param reasonText Reason to close the connection.
     * @param webSocketResponder WebSocket Responder is necessary if the implementation needs WebSocket server-push.
     *                           Otherwise leave it null.
     */
    public CloseWebSocketMessage(int statusCode, String reasonText, WebSocketResponder webSocketResponder) {
        super(webSocketResponder);
        this.statusCode = statusCode;
        this.reasonText = reasonText;
    }

    /**
     * @return Reason for closing the connection.
     */
    public String getReasonText() {
        return reasonText;
    }

    /**
     * @return Status code of the reason to close the connection.
     */
    public int getStatusCode() {
        return statusCode;
    }
}

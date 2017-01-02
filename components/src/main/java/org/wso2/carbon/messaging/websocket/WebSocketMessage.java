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

import org.wso2.carbon.messaging.CarbonMessage;

/**
 * {@link CarbonMessage} type of WebSocket Messages.
 * @since 1.0.0
 */
public class WebSocketMessage extends CarbonMessage {

    private WebSocketResponder webSocketResponder;


    /**
     * In WebSocket protocol there should be a capability to send server events without clinet request.
     * So it is necessary to add {@link WebSocketResponder} for the Constructor.
     * @param webSocketResponder Responder for a given channel.
     */
    public WebSocketMessage(WebSocketResponder webSocketResponder) {
        this.webSocketResponder = webSocketResponder;
    }

    /**
     * @return {@link WebSocketResponder} of a given channel.
     */
    public WebSocketResponder getWebSocketResponder() {
        return webSocketResponder;
    }
}

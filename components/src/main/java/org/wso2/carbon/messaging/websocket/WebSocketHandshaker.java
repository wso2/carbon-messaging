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
 * This interface is responsible for handling the Handshake.
 * This interface implementation should be added as a property to {@link org.wso2.carbon.messaging.CarbonMessage}
 * in and should be sent to application and if all the needs are satisfied the handshake can be done.
 * @since 1.0.0
 */
public interface WebSocketHandshaker {
    /**
     * If all necessities are satisfied call this method to handshake.
     * @return true when the handshake is completed properly.
     */
    boolean handshake();

    /**
     * Call this unless all necessities are completed to do the handshake.
     */
    void cancel();
}

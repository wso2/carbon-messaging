/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import java.nio.ByteBuffer;

/**
 * {@link CarbonMessage} for control messages. This is used for control messages of a connection.
 * This is just a extend of {@link BinaryCarbonMessage} since all the methods are same for both.
 * This message type is useful when you need to send control messages for a given connection.
 */
public class ControlCarbonMessage extends BinaryCarbonMessage {
    /**
     * @param bytes              byte array of binary data.
     * @param finalFragment      true if the message is the final fragment of the binary message. First fragment can
     *                           also be the final fragment.
     */
    public ControlCarbonMessage(ByteBuffer bytes, boolean finalFragment) {
        super(bytes, finalFragment);
    }
}

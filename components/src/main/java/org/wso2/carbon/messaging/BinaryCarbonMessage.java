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
 * {@link CarbonMessage} type for Binary Messages. This message type handles the binary data easily. So if you are
 * dealing with binary data it is better to use this message type. This will work as a binary data carrier from
 * transport level to application level.
 */
public class BinaryCarbonMessage extends CarbonMessage {

    private ByteBuffer bytes; //ByteBuffer to store binary data
    private boolean finalFragment; //Check whether given fragment is final when partial messages are sent

    /**
     * @param bytes byte array of binary data.
     * @param finalFragment true if the message is the final fragment of the binary message.
     *                      First fragment can also be the final fragment.
     */
    public BinaryCarbonMessage(ByteBuffer bytes, boolean finalFragment) {
        this.bytes = bytes;
        this.finalFragment = finalFragment;
    }

    /**
     * @return byte array of binary data contained in the message.
     */
    public ByteBuffer readBytes() {
        return bytes;
    }

    /**
     * @return true if the message is the final fragment of the binary message.
     */
    public boolean isFinalFragment() {
        return finalFragment;
    }

    @Override
    public ByteBuffer getMessageBody() {
        setAlreadyRead(true);
        return bytes;
    }
}

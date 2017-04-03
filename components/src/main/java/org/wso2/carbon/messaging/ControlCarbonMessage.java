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
 * This message type is useful when you need to send control messages for a given connection.
 */
public class ControlCarbonMessage extends CarbonMessage {

    private final String controlSignal;
    private final ByteBuffer buffer;
    private final boolean finalFragment;

    /**
     * @param controlSignal      String saying what kind of message signal it is.
     */
    public ControlCarbonMessage(String controlSignal) {
        this.controlSignal = controlSignal;
        this.buffer = null;
        this.finalFragment = true;
    }

    /**
     * @param buffer             byte bufffer of binary data.
     * @param finalFragment      true if the message is the final fragment of the binary message. First fragment can
     *                           also be the final fragment.
     */
    @Deprecated
    public ControlCarbonMessage(ByteBuffer buffer, boolean finalFragment) {
        this.controlSignal = null;
        this.buffer = buffer;
        this.finalFragment = finalFragment;
    }

    /**
     * This constructor is mostly used by the heart beat checks.
     * @param controlSignal      String saying what kind of message signal it is.
     * @param buffer             byte buffer of binary data.
     * @param finalFragment      true if the message is the final fragment of the binary message. First fragment can
     *                           also be the final fragment.
     */
    public ControlCarbonMessage(String controlSignal, ByteBuffer buffer, boolean finalFragment) {
        this.controlSignal = controlSignal;
        this.buffer = buffer;
        this.finalFragment = finalFragment;
    }

    /**
     * Retrieve the control signal.
     * @return the control signal if exists else null.
     */
    public String getControlSignal() {
        return controlSignal;
    }

    /**
     * Retrieve the bytes sent or received in controlling.
     * @return byte buffer of the control data.
     */
    public ByteBuffer readBytes() {
        return buffer;
    }

    /**
     * Retrieve whether this control message include the final fragment of control data.
     * @return true if this is the final fragment of control data else false.
     */
    public boolean isFinalFragment() {
        return finalFragment;
    }
}

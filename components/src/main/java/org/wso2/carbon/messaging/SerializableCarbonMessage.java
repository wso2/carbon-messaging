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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * {@link CarbonMessage} serializable implementation. This message type can be used when it is
 * required to serialize carbon messages.
 */
public class SerializableCarbonMessage extends CarbonMessage implements Serializable {

    private static final long serialVersionUID = 1;
    private String payload;
    private String payloadType;
    private HashMap<String, String> headers;

    /**
     * To get a map of header values.
     *
     * @return Map containing headers
     */
    public HashMap<String, String> getHeadersMap() {
        return headers;
    }

    /**
     * To set header values as a map.
     *
     * @param headers Map containing headers to be set
     */
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    /**
     * To get the payload of the message.
     *
     * @return Message payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * To set the payload of the message.
     *
     * @param payload Payload string to be set
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * To get the payload type of the message.
     *
     * @return String payload type
     */
    public String getPayloadType() {
        return payloadType;
    }

    /**
     * To set the payload type of the message.
     *
     * @param payloadType String payload type
     */
    public void setPayloadType(String payloadType) {
        this.payloadType = payloadType;
    }

    @Override
    public InputStream getInputStream() {
        if (payload == null) {
            return null;
        }
        return new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8));
    }
}

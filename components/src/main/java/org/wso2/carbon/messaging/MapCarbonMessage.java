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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link CarbonMessage} type for Map message. This message type is better if you are dealing with
 * map types of messages.
 */
public class MapCarbonMessage extends CarbonMessage {
    private Map<String, String> mapData;

    /**
     * Initializes a Map Carbon Message.
     */
    public MapCarbonMessage() {
        mapData = new HashMap<>();
    }

    /**
     * To set a value in the map.
     *
     * @param mapKeyName Key that need to be added to map
     * @param mapValue   Value of the key that need to be added to map
     */
    public void setValue(String mapKeyName, String mapValue) {
        mapData.put(mapKeyName, mapValue);
    }

    /**
     * To get the value of a particular key.
     *
     * @param mapKeyName Key to get the value for
     * @return the value for the specific key
     */
    public String getValue(String mapKeyName) {
        return mapData.get(mapKeyName);
    }

    /**
     * Return all the keys in the {@link MapCarbonMessage} object.
     *
     * @return an enumeration of all the keys in the {@link MapCarbonMessage}
     */
    public Enumeration<String> getMapNames() {
        return Collections.enumeration(mapData.keySet());
    }

    /**
     * Clear internal payload of the message
     */
    public void clearMapPayload() {
        mapData = new HashMap<>();
    }
}

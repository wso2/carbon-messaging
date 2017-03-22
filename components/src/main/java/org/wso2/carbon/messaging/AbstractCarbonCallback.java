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

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementation for the CarbonCallback.
 * Methods of this class are not thread-safe
 *
 * @since 2.3.2
 */
public abstract class AbstractCarbonCallback implements CarbonCallback {

    protected Map<String, Object> properties = new HashMap<>();

    /**
     * Get a property associated with the callback
     *
     * @param key property name
     * @return property for a particular key
     */
    public Object getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Get all the properties associated with the callback
     *
     * @return map of all the properties
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Set a property which is associated with the callback
     *
     * @param key   key of the property
     * @param value value of the property
     */
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    /**
     * Remove a property associated with the callback
     *
     * @param key key of the property
     */
    public void removeProperty(String key) {
        properties.remove(key);
    }

}

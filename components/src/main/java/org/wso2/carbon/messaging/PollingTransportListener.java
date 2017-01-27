/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.messaging;

import java.util.Map;

/**
 * Abstract class for the polling type of transport listeners.
 */
public abstract class PollingTransportListener extends TransportListener {

    public PollingTransportListener(String id) {
        super(id);
    }

    /**
     * Generic listen method which accepts the configuration metadata that will be used when listening.
     * @param parameters data to be used when starting to listen.
     */
    public abstract void listen(Map<String, String> parameters);
}

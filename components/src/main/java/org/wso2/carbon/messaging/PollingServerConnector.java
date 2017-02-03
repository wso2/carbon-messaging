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
 * Abstract class for the polling type of server connectors.
 */
public abstract class PollingServerConnector extends ServerConnector {

    private final long interval;
    private PollingTaskRunner inboundRunner;

    private Map<String, String> parameters;

    public PollingServerConnector(String id, long interval) {
        super(id);
        this.interval = interval;
    }

    /**
     * Call this method to start polling.
     * @param parameters Any parameters that may be required to be referred to
     *                   within the poll() method
     */
    public void startPolling(Map<String, String> parameters) {
        this.parameters = parameters;
        inboundRunner = new PollingTaskRunner(this);
        Thread runningThread = new Thread(inboundRunner);
        runningThread.start();
    }

    /**
     * Call this method when polling needs to be stopped.
     */
    public void destroy() {
        if (inboundRunner != null) {
            inboundRunner.terminate();
        }
    }

    /**
     * Specific {@link PollingServerConnector} should implement this method,
     * specifying what should be done in the poll
     */
    protected abstract void poll();

    protected long getInterval() {
        return interval;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}

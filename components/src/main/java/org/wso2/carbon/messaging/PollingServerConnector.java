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

import org.wso2.carbon.messaging.exceptions.ServerConnectorException;

/**
 * Abstract class for the polling type of server connectors.
 */
public abstract class PollingServerConnector extends ServerConnector {
    private long interval = 1000L;  //default polling interval
    private PollingTaskRunner pollingTaskRunner;


    public PollingServerConnector(String id) {
        super(id);
    }

    @Override
    public void stop() throws ServerConnectorException {
        if (pollingTaskRunner != null) {
            pollingTaskRunner.terminate();
        }
    }

    /**
     * The start polling method which should be called when starting the polling with given interval.
     * @param interval polling interval
     */
    public void startPoll(long interval) {
        this.interval = interval;
        pollingTaskRunner = new PollingTaskRunner(this);
        Thread runningThread = new Thread(pollingTaskRunner);
        runningThread.start();
    }

    /**
     * Generic polling method which will be invoked with each polling invocation.
     */
    public abstract void poll();


    protected long getInterval() {
        return interval;
    }
}

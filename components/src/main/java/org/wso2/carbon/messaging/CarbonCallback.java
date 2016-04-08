/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

/**
 * Callbacks are created in request path for execute in response path.
 * When response arrives callback methods should execute.
 */
public interface CarbonCallback {

    /**
     * Calls in response path   to do work for response.
     *
     * @param cMsg CarbonMessage to be processed.
     */
    void done(CarbonMessage cMsg);

    /**
     * If this is set to true then logic executes in separate thread either in Disruptor or Thread pool
     * @param value
     */
    void workInSeparateThread(boolean value);
}

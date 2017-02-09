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

/**
 * {@code ServerConnectorErrorHandler} is the interface for protocol specific error handlers in ballerina.
 * This error handler interface should be implemented for handling transport listener related errors.
 * This will be used by the application for a given transport protocol associated with a {@code ServerConnector}
 * implementation.
 */
public interface ServerConnectorErrorHandler {
    /**
     * The error handling method which will be invoked by the application using the {@code ServerConnector}
     * associated with this error handler.
     * @param exception error thrown from the application level to be handled.
     * @param carbonMessage the carbonMessage associated with the currently request/response flow
     * @param callback the callback used with responding
     * @throws Exception thrown, if the error could not be handled.
     */
    void handleError(Exception exception, CarbonMessage carbonMessage, CarbonCallback callback) throws Exception;

    /**
     * Returns the string value of the transport protocol (eg: "http", "jms", etc. ) this listener is bound to.
     * @return transport protocol
     */
    String getProtocol();
}

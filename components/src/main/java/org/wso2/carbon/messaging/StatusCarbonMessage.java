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
 * {@link CarbonMessage} type for Status Message. This contains a status, status code and if prefers a reason text
 */
public class StatusCarbonMessage extends CarbonMessage {

    private final String status;
    private final String reasonText; //Reason saying why the connection is closed
    private final int statusCode; //Status code of the connection close

    /**
     * @param statusCode Status code of reason to close.
     * @param reasonText Reason to close the connection.
     */
    public StatusCarbonMessage(String status, int statusCode, String reasonText) {
        this.status = status;
        this.statusCode = statusCode;
        this.reasonText = reasonText;
    }

    /**
     * @return the status of a connection
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return Reason for closing the connection.
     */
    public String getReasonText() {
        return reasonText;
    }

    /**
     * @return Status code of the reason to close the connection.
     */
    public int getStatusCode() {
        return statusCode;
    }
}

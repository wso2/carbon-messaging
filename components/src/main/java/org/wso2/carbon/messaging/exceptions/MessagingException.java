/**
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 **/


package org.wso2.carbon.messaging.exceptions;

/**
 * MessagingException is generic exception wrapper.
 *
 * An Exception that is originating from Transport level, can be wrapped as MessagingException. A CarbonMessage can be
 * made faulty by setting a MessagingException.
 *
 * MessagingException creator <b>must</b> assign an unique {@link #errorCode} for each faulty scenario.
 * This error code can be used to handle fault handling scenarios like Circuit Breaker.
 *
 * Eg: ( Demonstration purpose only. Refer CarbonMessageProcess implantation for specific errorCodes)
 *
 * 10100  HTTP Connection timeout.
 * 10101  HTTP Connection Failed.
 * 10102  HTTP Connection Failed. etc.
 */
public class MessagingException extends Exception {

    private int errorCode;

    public MessagingException(int errorCode) {
        this.errorCode = errorCode;
    }

    public MessagingException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MessagingException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public MessagingException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public MessagingException(String message, Throwable cause, boolean enableSuppression, boolean
            writableStackTrace, int errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

   /* Getters */

    public int getErrorCode() {
        return errorCode;
    }

}

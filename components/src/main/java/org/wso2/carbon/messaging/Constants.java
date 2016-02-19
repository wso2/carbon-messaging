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
 * Common constants used in message property.
 */
public final class Constants {

    public static final String DISRUPTOR = "Disruptor";

    public static final String PROTOCOL_NAME = "http";

    public static final String PROTOCOL = "PROTOCOL";

    public static final String HTTP_VERSION = "HTTP_VERSION";

    public static final String HTTP_METHOD = "HTTP_METHOD";

    public static final String HTTP_CONTENT_TYPE = "Content-Type";

    public static final String TEXT_XML = "text/xml";

    public static final String TEXT_PLAIN = "text/plain";

    public static final String APPLICATION_XML = "application/xml";

    public static final String GZIP = "gzip";

    public static final String HTTP_CONTENT_LENGTH = "Content-Length";

    public static final String HTTP_TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String HTTP_CONNECTION = "Connection";

    public static final String KEEP_ALIVE = "keep-alive";

    public static final String HTTP_SOAP_ACTION = "SOAPAction";

    public static final String HTTP_CONTENT_ENCODING = "Accept-Encoding";

    public static final String HTTP_HOST = "Host";

    public static final String TRANSPORT_HEADERS = "TRANSPORT_HEADERS";

    public static final String HTTP_STATUS_CODE = "HTTP_STATUS_CODE";

    public static final String CHNL_HNDLR_CTX = "CHNL_HNDLR_CTX";

    public static final String SRC_HNDLR = "SRC_HNDLR";

    public static final String RESPONSE_CALLBACK = "RESPONSE_CALLBACK";

    public static final String HOST = "HOST";

    public static final String PORT = "PORT";

    public static final String TO = "TO";

    public static final String DIRECTION = "DIRECTION";

    public static final String DIRECTION_REQUEST = "DIRECTION_REQUEST";

    public static final String DIRECTION_RESPONSE = "DIRECTION_RESPONSE";

    public static final String CALL_BACK = "CALL_BACK";

    public static final String EXCHANGE = "EXCHANGE";

    public static final String LISTENER_PORT = "LISTENER_PORT";

    private Constants() {
    }
}

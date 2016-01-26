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

    // Latency Metrics parameters

    public static final String TYPE_SERVER_REQUEST = "TYPE_SERVER_REQUEST";

    public static final String TYPE_CLIENT_REQUEST = "TYPE_CLIENT_REQUEST";

    public static final String TYPE_CLIENT_RESPONSE = "TYPE_CLIENT_RESPONSE";

    public static final String TYPE_SERVER_RESPONSE = "TYPE_SERVER_RESPONSE";

    public static final String TYPE_SERVER_CONNECTION = "TYPE_SERVER_CONNECTION";

    public static final String TYPE_CLIENT_CONNECTION = "TYPE_CLIENT_CONNECTION";

    public static final String SERVER_REQUEST_METRICS_HOLDER = "SERVER_REQUEST_METRICS_HOLDER";

    public static final String SERVER_RESPONSE_METRICS_HOLDER = "SERVER_RESPONSE_METRICS_HOLDER";

    public static final String CLIENT_RESPONSE_METRICS_HOLDER = "CLIENT_RESPONSE_METRICS_HOLDER";

    public static final String CLIENT_REQUEST_METRICS_HOLDER = "CLIENT_REQUEST_METRICS_HOLDER";

    public static final String RESPONSE_METRICS_HOLDER = "RESPONSE_METRICS_HOLDER";

    public static final String SERVER_CONNECTION_METRICS_HOLDER = "SERVER_CONNECTION_METRICS_HOLDER";

    public static final String CLIENT_CONNECTION_METRICS_HOLDER = "CLIENT_CONNECTION_METRICS_HOLDER";

    public static final String REQUEST_LIFE_TIMER = "request.life.timer";

    public static final String REQUEST_BODY_READ_TIMER = "request.body.read.timer";

    public static final String REQUEST_HEADER_READ_TIMER = "request.header.read.timer";

    public static final String REQUEST_BODY_WRITE_TIMER = "request.body.write.timer";

    public static final String REQUEST_HEADER_WRITE_TIMER = "request.header.write.timer";

    public static final String RESPONSE_LIFE_TIMER = "response.life.timer";

    public static final String RESPONSE_HEADER_READ_TIMER = "response.header.read.timer";

    public static final String RESPONSE_BODY_READ_TIMER = "response.body.read.timer";

    private Constants() {
    }
}

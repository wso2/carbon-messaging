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
 * This enumerator represents the location in which a given engage method is invoked.
 */
public enum EngagedLocation {
    CLIENT_CONNECTION_INITIATED,
    CLIENT_CONNECTION_COMPLETED,
    SERVER_CONNECTION_INITIATED,
    SERVER_CONNECTION_COMPLETED,
    CLIENT_REQEST_READ_INITIATED,
    CLIENT_REQUEST_READ_COMPLETED,
    SERVER_REQUEST_WRITE_INITIATED,
    SERVER_REQUEST_WIRTE_COMPLETED,
    SERVER_RESPONSE_READ_INITIATED,
    SERVER_RESPONSE_READ_COMPLETED,
    CLIENT_REQUEST_WRITE_INITIATED,
    CLIENT_REQUEST_WRITE_COMPLETED,
}

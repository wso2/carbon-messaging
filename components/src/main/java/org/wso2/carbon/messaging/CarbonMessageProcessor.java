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
 * Interface of the Carbon Message Processor which is used to process Carbon Messages.
 */
public interface CarbonMessageProcessor {

    /**
     * @param msg      CarbopnMessage received for processing.
     * @param callback Callback recived from transport layer to the engine.
     * @return void
     * @throws Exception Exception to signal any failure at the message processor.
     */
    boolean receive(CarbonMessage msg, CarbonCallback callback) throws Exception;

    void setTransportSender(TransportSender sender);

    String getId();

}

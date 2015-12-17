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

package org.wso2.carbon.messaging.internal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.kernel.transports.CarbonTransport;
import org.wso2.carbon.messaging.CarbonMessageProcessor;
import org.wso2.carbon.messaging.TransportListener;
import org.wso2.carbon.messaging.TransportSender;

/**
 * service component to refer the registered Engine, transport listener and transport sender
 */
@Component(
        name = "org.wso2.carbon.messaging.service.MessagingServiceComponent",
        immediate = true
)
@SuppressWarnings("unused")
public class MessagingServiceComponent {

    @Reference(
            name = "message-processor",
            service = CarbonMessageProcessor.class,
            cardinality = ReferenceCardinality.OPTIONAL,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "removeMessageProcessor"
    )
    protected void addMessageProcessor(CarbonMessageProcessor carbonMessageProcessor) {
        ContextHolder.getInstance().addMessageProcessor(carbonMessageProcessor);
    }

    protected void removeMessageProcessor(CarbonMessageProcessor carbonMessageProcessor) {
        ContextHolder.getInstance().removeMessageProcessor(carbonMessageProcessor);
    }

    @Reference(
            name = "transport-sender",
            service = TransportSender.class,
            cardinality = ReferenceCardinality.OPTIONAL,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "removeTransportSender"
    )
    protected void addTransportSender(TransportSender transportSender) {
        ContextHolder.getInstance().addTransportSender(transportSender);
    }

    protected void removeTransportSender(TransportSender transportSender) {
        ContextHolder.getInstance().removeTransportSender(transportSender);
    }

    @Reference(
            name = "transport-listener",
            service = CarbonTransport.class,
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "removeTransportListener"
    )
    protected void addTransportListener(TransportListener transportListener) {
        ContextHolder.getInstance().addTransportListener(transportListener);
    }

    protected void removeTransportListener(TransportListener transportListener) {
        ContextHolder.getInstance().removeTransportListener(transportListener);
    }

}

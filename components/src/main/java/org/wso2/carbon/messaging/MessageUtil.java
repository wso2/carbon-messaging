/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 */

package org.wso2.carbon.messaging;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * A class which has utility methods for manipulate messages
 */
public class MessageUtil {

    /**
     * Copy Message properties and transport headers
     *
     * @param carbonMessage CarbonMessage
     * @return CarbonMessage
     */
    public static CarbonMessage cloneCarbonMessageWithOutData(CarbonMessage carbonMessage) {

        CarbonMessage newCarbonMessage = new DefaultCarbonMessage(carbonMessage.isBufferContent());

        List<Header> transportHeaders = carbonMessage.getHeaders().getClone();

        newCarbonMessage.setHeaders(transportHeaders);

        Map<String, Object> propertiesMap = carbonMessage.getProperties();

        propertiesMap.forEach((key, value) -> newCarbonMessage.setProperty(key, value));

        newCarbonMessage.setWriter(carbonMessage.getWriter());
        newCarbonMessage.setFaultHandlerStack(carbonMessage.getFaultHandlerStack());
        return newCarbonMessage;
    }

    /**
     * Copy the Full carbon message with data
     *
     * @param carbonMessage CarbonMessage
     * @return carbonMessage
     */
    public static CarbonMessage cloneCarbonMessageWithData(CarbonMessage carbonMessage) {

        CarbonMessage newCarbonMessage = new DefaultCarbonMessage(carbonMessage.isBufferContent());

        List<Header> transportHeaders = carbonMessage.getHeaders().getClone();

        newCarbonMessage.setHeaders(transportHeaders);

        Map<String, Object> propertiesMap = carbonMessage.getProperties();

        propertiesMap.forEach((key, value) -> newCarbonMessage.setProperty(key, value));

        newCarbonMessage.setWriter(carbonMessage.getWriter());
        newCarbonMessage.setFaultHandlerStack(carbonMessage.getFaultHandlerStack());

        carbonMessage.getCopyOfFullMessageBody().forEach(buffer -> newCarbonMessage.addMessageBody(buffer));
        newCarbonMessage.setEndOfMsgAdded(true);
        return newCarbonMessage;
    }

    public static ByteBuffer deepCopy(ByteBuffer orig) {
        int pos = orig.position(), lim = orig.limit();
        try {
            orig.position(0).limit(orig.capacity()); // set range to entire buffer
            ByteBuffer toReturn = deepCopyVisible(orig); // deep copy range
            toReturn.position(pos).limit(lim); // set range to original
            return toReturn;
        } finally { // do in finally in case something goes wrong we don't bork the orig

            orig.position(pos).limit(lim); // restore original
        }
    }

    public static ByteBuffer deepCopyVisible(ByteBuffer orig) {
        int pos = orig.position();
        try {
            ByteBuffer toReturn;
            // try to maintain implementation to keep performance
            if (orig.isDirect()) {
                toReturn = ByteBuffer.allocateDirect(orig.remaining());
            } else {
                toReturn = ByteBuffer.allocate(orig.remaining());
            }

            toReturn.put(orig);
            toReturn.order(orig.order());

            return (ByteBuffer) toReturn.position(0);
        } finally {
            orig.position(pos);
        }
    }

}

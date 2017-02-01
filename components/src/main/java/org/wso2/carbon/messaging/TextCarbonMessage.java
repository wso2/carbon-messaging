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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * {@link CarbonMessage} type for Text Messages. This message type is better if you are dealing with
 * text data. This will work as the text data carrier from transport level to application level.
 */
public class TextCarbonMessage extends CarbonMessage {

    private final String text;

    /**
     * @param text Text Message
     */
    public TextCarbonMessage(String text) {
        this.text = text;
    }

    /**
     * @return String included in Text Message.
     */
    public String getText() {
        return text;
    }
    
    @Override
    public InputStream getInputStream() {
        if (text == null) {
            return null;
        }
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }
}

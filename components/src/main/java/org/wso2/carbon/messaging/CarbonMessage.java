package org.wso2.carbon.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 * Data carrier between the components.
 */
public abstract class CarbonMessage {

    protected Map<String, String> headers = new HashMap<>();
    protected Map<String, Object> properties = new HashMap<>();

    Map<String, String> getHeaders() {
        return headers;
    }

    abstract <T> T[] getMessageBody();

    Object getPropertie(String key) {
        return (String) properties.get(key);
    }

    void setPropertie(String key, Object value) {
        properties.put(key, value);
    }
}

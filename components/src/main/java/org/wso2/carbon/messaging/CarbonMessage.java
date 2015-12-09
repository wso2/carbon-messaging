package org.wso2.carbon.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Data carrier between the components.
 *
 */
public class CarbonMessage {

    protected Map<String, String> headers = new HashMap<>();
    protected Map<String, Object> properties = new HashMap<>();

    protected List<byte[]> messageBody = new ArrayList<>();

    protected String stringMessageBody;

    public String getStringMessageBody() {
        return stringMessageBody;
    }

    public void setStringMessageBody(String stringMessageBody) {
        this.stringMessageBody = stringMessageBody;
    }

    public void setEndOfMessageAdded(boolean endOfMessageAdded) {
        this.endOfMessageAdded = endOfMessageAdded;
    }

    protected boolean endOfMessageAdded = false;

    public boolean isEndOfMessageAdded() {
        return endOfMessageAdded;
    }

    public Iterator<byte[]> getMessageBody() {
        return messageBody.iterator();
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody.add(messageBody);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }


//    public abstract <T> T[] getMessageBody();

    public Object getProperty(String key) {
        if (properties != null) {
            return properties.get(key);
        } else {
            return null;
        }
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }
}

package org.wso2.carbon.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Data carrier between the components.
 */
public abstract class CarbonMessage {

    private static final Logger LOG = LoggerFactory.getLogger(CarbonMessage.class);

    protected Map<String, String> headers = new HashMap<>();
    protected Map<String, Object> properties = new HashMap<>();
    protected BlockingQueue<ByteBuffer> messageBody = new LinkedBlockingQueue<>();

    private boolean eomAdded = false;

    public boolean isEomAdded() {
        return eomAdded;
    }

    public void setEomAdded(boolean eomAdded) {
        this.eomAdded = eomAdded;
    }

    public boolean isEmpty() {
        return messageBody.isEmpty();
    }

    public ByteBuffer getMessageBody() {
        try {
            return messageBody.take();
        } catch (InterruptedException e) {
            LOG.error("Error while retrieving chunk from queue.", e);
            return null;
        }

    }

    public void addMessageBody(ByteBuffer msgBody) {
        messageBody.add(msgBody);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

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

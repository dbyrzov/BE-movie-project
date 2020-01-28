package com.movieproject.bean;

import java.util.HashMap;
import java.util.Map;

public class BodyHTTP {
    public Map<String, String> headers;

    public BodyHTTP() {
        this.headers = new HashMap<>();
    }

    public void setPair(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder body = new StringBuilder("{ \"headers\": [");
        int i = 0;

        for (Map.Entry<String, String> map : this.headers.entrySet()) {
            String m = "{\"key\":\"" + map.getKey() + "\",\"value\":\"" + map.getValue() + "\"}";
            if (i > 0) { body.append(","); }
            i = 1;
            body.append(m);
        }
        body.append("]}");
        return body.toString();
    }
}

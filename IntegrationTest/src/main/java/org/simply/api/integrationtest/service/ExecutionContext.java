package org.simply.api.integrationtest.service;

import java.util.HashMap;
import java.util.Map;

import static java.lang.ThreadLocal.withInitial;

public enum ExecutionContext {

    CONTEXT;

    private final ThreadLocal<Map<String, Object>> contexts = withInitial(HashMap::new);

    public <T> T get(String name) {
        return (T) contexts.get().get(name);
    }

    public void set(String name, Object data) {
        contexts.get().put(name, data);
    }

    public void reset() {
        contexts.get().clear();
    }
}

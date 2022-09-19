package org.simply.api.integrationtest;

import org.junit.Test;
import org.simply.api.integrationtest.service.ExecutionContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class ExecutionContextTest {

    private ExecutionContext target = ExecutionContext.CONTEXT;

    @Test
    public void testKeyFound() {
        target.set("key", 101);

        assertEquals(Integer.valueOf(101), target.get("key"), "Value of key should be 101");
    }

    @Test
    public void testKeyNotFound() {

        assertNull(target.get("key"), "Key should not be found");
    }

    @Test
    public void testKeyShouldNotFoundAfterReset() {
        target.set("key", 101);

        assertEquals(Integer.valueOf(101), target.get("key"), "Value of key should be 101");

        target.reset();

        assertNull(target.get("key"), "Key should not be found");
    }
}

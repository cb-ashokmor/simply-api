package org.simply.api.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Counter {

    private AtomicInteger counter = new AtomicInteger();

    private Long start;

    private Long end;

    public void start() {
        if (start == null) {
            start = System.currentTimeMillis();
        }
    }

    public void end() {
        end = System.currentTimeMillis();
    }

    public int incrementAndGet() {
        return counter.incrementAndGet();
    }

    public void reset() {
        counter = new AtomicInteger();
        start = null;
        end = null;
    }

    public long duration() {
        return (end != null && start != null) ? end - start : 0;
    }

    @Override
    public String toString() {
        return "counter: " + counter;
    }
}

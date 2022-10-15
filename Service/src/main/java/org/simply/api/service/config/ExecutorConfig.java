package org.simply.api.service.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExecutorConfig {
    private int corePoolSize;
    private int maxPoolSize;
}

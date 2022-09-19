package org.simply.api.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simply.api.common.model.Error;

@AllArgsConstructor
@Getter
public class IllegalArgumentException extends RuntimeException {
    private Error error;
}

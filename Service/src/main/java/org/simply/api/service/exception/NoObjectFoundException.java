package org.simply.api.service.exception;

import lombok.Builder;
import lombok.Getter;
import org.simply.api.common.model.Error;

@Getter
@Builder
public class NoObjectFoundException extends RuntimeException {

    private Error error;
}

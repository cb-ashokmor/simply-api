package org.simply.api.common.model.job;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {

    private String message;
    private Date begin;
    private Date end;
    private int totalRequest;
    private int successful;
    private float duration;
}
package org.simply.api.common.model.job;

import lombok.*;
import org.springframework.http.ResponseEntity;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AsyncJobProcessResult {

    private ResponseEntity<String> responseEntity;
    private int id;
}

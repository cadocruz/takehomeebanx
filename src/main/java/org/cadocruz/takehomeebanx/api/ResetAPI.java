package org.cadocruz.takehomeebanx.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "reset")
public interface ResetAPI {

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<String> resetAccount();
}

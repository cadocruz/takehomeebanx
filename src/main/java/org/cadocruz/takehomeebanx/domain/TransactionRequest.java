package org.cadocruz.takehomeebanx.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TransactionRequest {
    private TypeOperation type;
    private String destination;
    private String origin;
    private Long amount;
}

package org.cadocruz.takehomeebanx.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@AllArgsConstructor
public abstract class TransactionInput {
    private TypeOperation type;
    private Optional<String> origin;
    private Optional<String> destination;
    private Long amount;
}

package org.cadocruz.takehomeebanx.domain;

import java.util.Optional;

public class TransactionRequest extends TransactionInput {
    public TransactionRequest(TypeOperation type, Optional<String> origin, Optional<String> destination, Long amount) {
        super(type, origin, destination, amount);
    }
}

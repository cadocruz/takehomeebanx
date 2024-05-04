package org.cadocruz.takehomeebanx.service.handler;

import org.cadocruz.takehomeebanx.domain.TransactionInput;
import org.cadocruz.takehomeebanx.domain.TransactionOutput;

public interface TransactionCommand {
    TransactionOutput execute(TransactionInput anInput);
}

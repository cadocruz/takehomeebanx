package org.cadocruz.takehomeebanx.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class TransactionOutput {
    private Account origin;
    private Account destination;
}

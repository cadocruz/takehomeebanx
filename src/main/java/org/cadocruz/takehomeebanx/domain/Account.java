package org.cadocruz.takehomeebanx.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Account {
    private String id;
    private Long balance;

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}

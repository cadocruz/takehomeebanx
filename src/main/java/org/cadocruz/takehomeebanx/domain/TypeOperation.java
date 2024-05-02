package org.cadocruz.takehomeebanx.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum TypeOperation {
        DEPOSIT("deposit"),
        WITHDRAW("withdraw"),
        TRANSFER("transfer");

        private String name;

        @JsonCreator
        public static TypeOperation forValue(String value) {
                return Stream.of(TypeOperation.values())
                        .filter(enumValue -> enumValue.name().equals(value.toUpperCase()))
                        .findFirst()
                        .orElse(null);
        }
}

package org.asep.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyDto {

    private String code;
    private String name;
}
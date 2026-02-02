package org.asep.finance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class HistoricalIdrUsdDto {

    private LocalDate date;
    private BigDecimal rate;
}

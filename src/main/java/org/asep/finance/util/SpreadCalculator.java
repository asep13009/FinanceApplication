package org.asep.finance.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
public class SpreadCalculator {

    private final double SPREAD_FACTOR;

    public SpreadCalculator( @Value("${github.username}") String githubUsername) {
        log.info("my github  = {}" , githubUsername);

        this.SPREAD_FACTOR = calculateSpreadFactor(githubUsername);

        log.info(">>> this my SPREAD_FACTOR = {}", SPREAD_FACTOR);
        log.info(">>> SPREAD_FACTOR saved in memory");
    }

    private double calculateSpreadFactor(String username) {
        int sum = username.toLowerCase()
                .chars()
                .sum();

        log.info(">>> sum of the Unicode (ASCII) values = {} ", sum);
        return (sum % 1000) / 100000.0;
    }

    public BigDecimal calculate(BigDecimal Rate_USD) {
        return (BigDecimal.ONE.divide(Rate_USD, 10, RoundingMode.HALF_UP))
                .multiply(
                        BigDecimal.valueOf(1 + SPREAD_FACTOR)
                );
    }

    public double getSpreadFactor() {
        return SPREAD_FACTOR;
    }
}


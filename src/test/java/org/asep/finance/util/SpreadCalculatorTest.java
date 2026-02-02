package org.asep.finance.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpreadCalculatorTest {
    @Test
    void shouldCalculateSpreadFactorCorrectly() {

        SpreadCalculator calculator =
                new SpreadCalculator("asep13009");

        assertEquals(0.00678, calculator.getSpreadFactor());
    }
}
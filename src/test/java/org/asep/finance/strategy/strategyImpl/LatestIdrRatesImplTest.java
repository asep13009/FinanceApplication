package org.asep.finance.strategy.strategyImpl;

import helper.WebClientTestUtil;
import org.asep.finance.dto.LatestIdrRateDto;
import org.asep.finance.util.SpreadCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LatestIdrRatesImplTest {

    @Test
    void shouldFetchAndCalculateSpreadCorrectly() {

        String responseJson = """
            {
              "rates": {
                "USD": 0.000064
              }
            }
        """;

        WebClient webClient = WebClientTestUtil.mockWebClient(responseJson);

        SpreadCalculator calculator =
                new SpreadCalculator("asep13009"); // spread = 0.00678

        LatestIdrRatesImpl fetcher =
                new LatestIdrRatesImpl(webClient, calculator);

        List<LatestIdrRateDto> result = fetcher.fetchData();

        assertEquals(1, result.size());

        LatestIdrRateDto dto = result.get(0);

        assertEquals("USD", dto.getCurrency());
        assertEquals(new BigDecimal("0.000064"), dto.getRate());

        // (1 / 0.000064) * (1 + 0.00678)
        BigDecimal expected =
                BigDecimal.ONE
                        .divide(new BigDecimal("0.000064"), 10, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("1.00678"));

        assertEquals(0, expected.compareTo(dto.getUsdBuySpreadIdr()));
    }
}
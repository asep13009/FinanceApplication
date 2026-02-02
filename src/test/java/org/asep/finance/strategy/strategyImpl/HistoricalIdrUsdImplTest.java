package org.asep.finance.strategy.strategyImpl;

import helper.WebClientTestUtil;
import org.asep.finance.dto.HistoricalIdrUsdDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoricalIdrUsdImplTest {


    @Test
    void shouldMapHistoricalRatesCorrectly() {

        String responseJson = """
            {
              "rates": {
                "2024-01-01": { "USD": 0.000065 },
                "2024-01-02": { "USD": 0.000066 }
              }
            }
        """;

        WebClient webClient = WebClientTestUtil.mockWebClient(responseJson);

        HistoricalIdrUsdImpl fetcher =
                new HistoricalIdrUsdImpl(webClient);

        List<HistoricalIdrUsdDto> result = fetcher.fetchData();

        assertEquals(2, result.size());

        assertEquals(
                LocalDate.of(2024, 1, 1),
                result.get(0).getDate()
        );

        assertEquals(
                new BigDecimal("0.000065"),
                result.get(0).getRate()
        );
    }

}
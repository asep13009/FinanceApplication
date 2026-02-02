package org.asep.finance.strategy.strategyImpl;

import helper.WebClientTestUtil;
import org.asep.finance.dto.CurrencyDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupportedCurrenciesImplTest {

    @Test
    void shouldMapCurrenciesCorrectly() {

        String responseJson = """
            {
              "USD": "United States Dollar",
              "IDR": "Indonesian Rupiah"
            }
        """;

        WebClient webClient = WebClientTestUtil.mockWebClient(responseJson);

        SupportedCurrenciesImpl fetcher =
                new SupportedCurrenciesImpl(webClient);

        List<CurrencyDto> result = fetcher.fetchData();

        assertEquals(2, result.size());

        assertEquals("USD", result.get(0).getCode());
        assertEquals("United States Dollar", result.get(0).getName());
    }
}
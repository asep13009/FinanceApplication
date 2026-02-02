package org.asep.finance.strategy.strategyImpl;

import lombok.RequiredArgsConstructor;
import org.asep.finance.strategy.IDRDataFetcher;
import org.asep.finance.dto.CurrencyDto;
import org.asep.finance.handling.exception.ExternalApiException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SupportedCurrenciesImpl implements IDRDataFetcher {

    private final WebClient webClient;

    @Override
    public String getResourceType() {
            return "supported_currencies";
    }

    @Override
    public List<CurrencyDto> fetchData() {
        try {
            Map<String, String> response = webClient.get()
                    .uri("/currencies")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return response.entrySet().stream()
                    .map(e -> new CurrencyDto(e.getKey(), e.getValue()))
                    .toList();

        } catch (Exception e) {
            throw new ExternalApiException("Failed to fetch currencies");
        }
    }
}

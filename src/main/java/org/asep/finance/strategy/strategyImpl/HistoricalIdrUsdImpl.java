package org.asep.finance.strategy.strategyImpl;

import lombok.RequiredArgsConstructor;
import org.asep.finance.strategy.IDRDataFetcher;
import org.asep.finance.dto.HistoricalIdrUsdDto;
import org.asep.finance.handling.exception.ExternalApiException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HistoricalIdrUsdImpl implements IDRDataFetcher {

    private final WebClient webClient;

    @Override
    public String getResourceType() {
        return "historical_idr_usd";
    }

    @Override
    public List<HistoricalIdrUsdDto> fetchData() {
        try {
            Map<String, Object> response = webClient.get()
                    .uri("/2024-01-01..2024-01-05?from=IDR&to=USD")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            Map<String, Map<String, Double>> rates =
                    (Map<String, Map<String, Double>>) response.get("rates");

            return rates.entrySet().stream()
                    .map(e -> new HistoricalIdrUsdDto(
                            LocalDate.parse(e.getKey()),
                            BigDecimal.valueOf(e.getValue().get("USD"))
                    ))
                    .toList();

        } catch (Exception e) {
            throw new ExternalApiException("Failed to fetch historical IDR USD data");
        }
    }
}

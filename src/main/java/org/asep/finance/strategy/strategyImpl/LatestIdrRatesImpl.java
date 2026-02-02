package org.asep.finance.strategy.strategyImpl;

import lombok.RequiredArgsConstructor;
import org.asep.finance.strategy.IDRDataFetcher;
import org.asep.finance.dto.LatestIdrRateDto;
import org.asep.finance.handling.exception.ExternalApiException;
import org.asep.finance.util.SpreadCalculator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LatestIdrRatesImpl implements IDRDataFetcher {

    private final WebClient webClient;
    private final SpreadCalculator spreadCalculator;

    @Override
    public String getResourceType() {
        return "latest_idr_rates";
    }

    @Override
    public List<LatestIdrRateDto> fetchData() {
        try {
            Map<String, Object> response = webClient.get()
                    .uri("/latest?base=IDR")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            Map<String, Double> rates =
                    (Map<String, Double>) response.get("rates");

            return rates.entrySet().stream()
                    .map(e -> new LatestIdrRateDto(
                            e.getKey(),
                            BigDecimal.valueOf(e.getValue()),
                            spreadCalculator.calculate(BigDecimal.valueOf(e.getValue()))

                    ))
                    .toList();

        } catch (Exception e) {
            throw new ExternalApiException("Failed to fetch latest IDR rates");
        }
    }
}

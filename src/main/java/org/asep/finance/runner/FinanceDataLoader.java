package org.asep.finance.runner;

import lombok.RequiredArgsConstructor;
import org.asep.finance.runner.registry.IDRDataFetcherRegistry;
import org.asep.finance.runner.store.FinanceDataStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FinanceDataLoader implements ApplicationRunner {

    private final IDRDataFetcherRegistry registry;
    private final FinanceDataStore store;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("loader .........");
        Map<String, List<?>> loaded = new HashMap<>();

        loaded.put("latest_idr_rates", registry.get("latest_idr_rates").fetchData());
        loaded.put("historical_idr_usd", registry.get("historical_idr_usd").fetchData());
        loaded.put("supported_currencies", registry.get("supported_currencies").fetchData());

        store.init(loaded);
    }
}

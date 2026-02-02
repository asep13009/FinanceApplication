package org.asep.finance.runner.registry;

import org.asep.finance.handling.exception.ResourceTypeNotFoundException;
import org.asep.finance.strategy.IDRDataFetcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class IDRDataFetcherRegistry {

    private final Map<String, IDRDataFetcher> fetchers;

    public IDRDataFetcherRegistry(List<IDRDataFetcher> list) {
        this.fetchers = list.stream()
                .collect(Collectors.toUnmodifiableMap(
                        IDRDataFetcher::getResourceType,
                        Function.identity()
                ));
    }

    public IDRDataFetcher get(String resourceType) {
        IDRDataFetcher fetcher = fetchers.get(resourceType);
        if (fetcher == null) {
            throw new ResourceTypeNotFoundException(resourceType);
        }
        return fetcher;
    }
}

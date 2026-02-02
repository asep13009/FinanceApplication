package org.asep.finance.service;

import lombok.RequiredArgsConstructor;
import org.asep.finance.handling.exception.ResourceTypeNotFoundException;
import org.asep.finance.runner.store.FinanceDataStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceDataService {

    private final FinanceDataStore store;

    public List<?> getData(String resourceType) {
        List<?> data = store.get(resourceType);
        if (data == null) {
            throw new ResourceTypeNotFoundException(resourceType);
        }
        return data;
    }
}

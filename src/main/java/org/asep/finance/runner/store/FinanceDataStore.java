package org.asep.finance.runner.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class FinanceDataStore {

    private volatile Map<String, List<?>> data = Map.of();

    public void init(Map<String, List<?>> loaded) {
        log.info(">>>>> data frankfurter save in memory <<<<<");
        this.data = Collections.unmodifiableMap(loaded);
    }

    public List<?> get(String key) {
        log.info(">>>>>  get frankfurter by store not hit external API <<<<<");
        return data.get(key);
    }
}

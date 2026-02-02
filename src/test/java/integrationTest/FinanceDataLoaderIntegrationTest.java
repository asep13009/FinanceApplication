package integrationTest;

import org.asep.finance.FinanceApplication;
import org.asep.finance.runner.store.FinanceDataStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FinanceApplication.class)
@Import(MockWebClientConfig.class)
class FinanceDataLoaderIntegrationTest {

    @Autowired
    private FinanceDataStore store;

    @Test
    void shouldLoadAllDataAtStartup() {
        List<?> latest = store.get("latest_idr_rates");
        List<?> historical = store.get("historical_idr_usd");
        List<?> currencies = store.get("supported_currencies");

        assertNotNull(latest);
        assertNotNull(historical);
        assertNotNull(currencies);

        assertFalse(latest.isEmpty());
    }
}
package org.asep.finance.strategy;

import java.util.List;

public interface IDRDataFetcher {

    String getResourceType();

    List<?> fetchData();
}

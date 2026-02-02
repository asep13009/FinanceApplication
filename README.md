# IDR Finance Data Aggregator – Spring Boot

This project is a Spring Boot REST API that aggregates multiple financial data resources from the public, 
keyless Frankfurter Exchange Rate API, with a primary focus on Indonesian Rupiah (IDR) data.

The application demonstrates clean architecture, polymorphism via the Strategy Pattern, thread-safe immutable data handling, startup data loading, and production-ready error handling.

## Features

* Single polymorphic REST endpoint for multiple resource types
* Integration with multiple Frankfurter API resources
* Strategy Pattern for extensible resource handling
* External API client built via custom FactoryBean
* Startup-time data ingestion using ApplicationRunner
* Immutable, thread-safe in-memory data store
* Global response wrapper and centralized exception handling
* Unit and integration test coverage

## Architecture Overview

### High-level flow:
```
Frankfurter API
▲
│ (WebClient via FactoryBean)
│
IDRDataFetcher Strategies
▲
│
ApplicationRunner (startup)
│
▼
Immutable In-Memory Store
│
▼
Service Layer
│
▼
REST Controller
```

## Key architectural decisions:

* Polymorphism over conditionals using the Strategy Pattern
* Single external API client configuration via FactoryBean
* Read-only API backed by preloaded, immutable data


## Tech Stack

* Java 17
* Spring Boot 3.x
* Spring WebFlux (WebClient)
* Maven
* JUnit 5 & Mockito


# Setup & Run Instructions
## Prerequisites
* Java 17+
* Maven 3.8+

## Clone & Run
```
git clone https://github.com/asep13009/FinanceApplication.git
cd finance
mvn clean test
mvn spring-boot:run
```

## The application will start on:
```
http://localhost:8080
```

## API Endpoint
#### Base Endpoint
```
GET /api/finance/data/{resourceType}
```


#### Supported resourceType values
```
Resource Type	                            Description

latest_idr_rates	                    Latest exchange rates with base IDR, including USD buy spread
historical_idr_usd	                    Historical IDR → USD rates (2024-01-01 to 2024-01-05)
supported_currencies	                    List of all supported currency symbols
```


### Example cURL Requests
#### Latest IDR Rates
```
curl http://localhost:8080/api/finance/data/latest_idr_rates
```

#### Historical IDR → USD
```
curl http://localhost:8080/api/finance/data/historical_idr_usd
```

#### Supported Currencies
```
curl http://localhost:8080/api/finance/data/supported_currencies
```

## API Response Format
### Success Response
```
{
"status": "SUCCESS",
"code": 200,
"message": "Request processed successfully",
"data": [...]
```

### Error Response
```
{
"status": "ERROR",
"code": 400,
"message": "Unsupported resourceType: abc",
"data": null
}
```

## USD Buy Spread Calculation (Personalization)

> GitHub Username: asep13009

### Calculation Steps

1. Convert username to lowercase
2. Sum ASCII values of all characters
```
a=97, s=115, e=101, p=112, 1=49, 3=51, 0=48, 0=48, 9=57
Total = 678
```

3. Spread Factor formula:
````
Spread Factor = (678 % 1000) / 100000.0
= 0.00678
````

### Final Formula
```
USD_BuySpread_IDR = (1 / Rate_USD) * (1 + Spread Factor)
```
*The spread factor is calculated dynamically at application startup based on the configured GitHub username.


## Architectural Rationale
### 1. Why Strategy Pattern?

The Strategy Pattern is used to handle multiple resource types (latest_idr_rates, historical_idr_usd, supported_currencies) behind a single API endpoint.

#### Benefits:

* Eliminates conditional logic (if/else, switch) in controller and service layers
* Improves extensibility when adding new resource types
* Encourages single responsibility and cleaner code organization

### 2. Why use a FactoryBean for the API client?

The Frankfurter API client (WebClient) is constructed via a custom FactoryBean to:
* Centralize configuration (base URL, timeouts, shared headers)
* Ensure all strategies share the same HTTP client instance
* Demonstrate advanced Spring lifecycle and bean construction concepts

This approach is more flexible and scalable than defining a simple @Bean.

### 3. Why ApplicationRunner instead of @PostConstruct?

ApplicationRunner is used for startup data ingestion because:
* The Spring application context is fully initialized
* Dependency injection is guaranteed to be complete
* Error handling and testing are more reliable
* It is better suited for controlled startup workflows than @PostConstruct

## Testing Strategy
### Unit Tests

* Each IDRDataFetcher strategy is tested in isolation
* External API calls are mocked using a custom WebClient ExchangeFunction
* Spread calculation and data transformation logic are fully covered

### Integration Test
* Verifies that ApplicationRunner runs at startup
* Ensures all required data is loaded into the immutable in-memory store
* Confirms the application is ready to serve requests after context initialization

## External API Client Configuration
* Base URL configured via application.yml 
* Connection, read, and write timeouts: 10000 ms
* Shared headers:
```
Accept: application/json
User-Agent: idr-rate-aggregator/1.0
```

## Summary

This project demonstrates:
* Clean, extensible architecture
* Advanced Spring concepts
* Thread-safe and immutable data handling 
* Robust error handling and testing practices 
* Production-ready API design
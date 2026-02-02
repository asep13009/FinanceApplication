package helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientTestUtil {

    public static WebClient mockWebClient(String jsonResponse) {

        ExchangeFunction exchangeFunction = request ->
                Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(jsonResponse)
                        .build());

        return WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();
    }


    public static WebClient mockWebClientByUri() {

        ExchangeFunction exchangeFunction = request -> {

            String path = request.url().getPath();

            String response;

            if (path.contains("/latest")) {
                response = """
                    {
                      "rates": {
                        "USD": 0.000064
                      }
                    }
                """;
            }
            else if (path.contains("2024-01-01")) {
                response = """
                    {
                      "rates": {
                        "2024-01-01": { "USD": 0.000065 },
                        "2024-01-02": { "USD": 0.000066 }
                      }
                    }
                """;
            }
            else if (path.contains("/currencies")) {
                response = """
                    {
                      "USD": "United States Dollar",
                      "IDR": "Indonesian Rupiah"
                    }
                """;
            }
            else {
                return Mono.error(new RuntimeException("Unknown URI"));
            }

            return Mono.just(
                    ClientResponse.create(HttpStatus.OK)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(response)
                            .build()
            );
        };

        return WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();
    }
}

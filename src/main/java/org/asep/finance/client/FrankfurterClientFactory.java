package org.asep.finance.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
@Slf4j
@Component
public class FrankfurterClientFactory implements FactoryBean<WebClient> {

    @Value("${frankfurter.base-url}")
    private String baseUrl;
    @Value("${frankfurter.timeout}")
    private int TIMEOUT_MS;
    @Value("${frankfurter.header.user.agent}")
    private String USER_AGENT_CLIENT;

    @Override
    public WebClient getObject() {
        log.info(">>>>> run API frankfurter at load application <<<<<");
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT_MS)
                .responseTimeout(Duration.ofMillis(TIMEOUT_MS))
                .doOnConnected(conn ->
                        conn.addHandlerLast(
                                        new ReadTimeoutHandler(TIMEOUT_MS, TimeUnit.MILLISECONDS))
                                .addHandlerLast(
                                        new WriteTimeoutHandler(TIMEOUT_MS, TimeUnit.MILLISECONDS))
                );
        log.info("baseUrl : {}", baseUrl);
        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT_CLIENT)
                .build();
    }

    @Override
    public Class<?> getObjectType() {
        return WebClient.class;
    }
}


package com.sebastian_daschner.coffee_shop.backend;

import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import jakarta.json.Json;
import jakarta.json.JsonReader;

import java.io.StringReader;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class Barista {

    public Barista() {
        String host = System.getProperty("test.barista.host", "localhost");
        int port = Integer.parseInt(System.getProperty("test.barista.port", "8002"));

        configureFor(host, port);

        stubFor(post("/processes").willReturn(okForJson(Map.of("status", "PREPARING"))));
    }

    public void answerForOrder(URI orderUri, String status) {
        String orderId = extractOrderId(orderUri);
        removeServeEvents(postRequestedFor(urlEqualTo("/processes")).withRequestBody(requestJson(orderId)));
        stubFor(post("/processes")
                .withRequestBody(requestJson(orderId))
                .willReturn(okForJson(Map.of("status", status))));
    }

    private StringValuePattern requestJson(String orderId) {
        return equalToJson("{\"order\":\"" + orderId + "\"}", true, true);
    }

    private StringValuePattern requestJson(String orderId, String status) {
        return equalToJson("{\"order\":\"" + orderId + "\",\"status\":\"" + status + "\"}", true, true);
    }

    public void waitForInvocation(URI orderUri, String status) {
        String orderId = extractOrderId(orderUri);
        await().atMost(10, SECONDS).until(() -> requestMatched(orderId, status));
    }

    private boolean requestMatched(String orderId, String status) {
        List<LoggedRequest> matchedRequests = findAll(postRequestedFor(urlEqualTo("/processes"))
                .withRequestBody(requestJson(orderId, status)));
        return !matchedRequests.isEmpty();
    }

    private String extractOrderId(URI orderUri) {
        String string = orderUri.toString();
        return string.substring(string.lastIndexOf('/') + 1);
    }

    public void debug(URI orderUri) {
        String orderId = extractOrderId(orderUri);
        getAllServeEvents().stream()
                .filter(e -> e.getRequest().getBodyAsString().contains(orderId))
                .forEach(e -> {
                    System.out.println(e.getRequest().getBodyAsString());
                    System.out.println(e.getResponse().getStatus());
                    System.out.println(e.getResponse().getBodyAsString());
                });
    }

    public void verifyRequests(URI orderUri, Order order) {
        String orderId = extractOrderId(orderUri);
        findAll(anyRequestedFor(anyUrl())).stream()
                .map(r -> {
                    JsonReader reader = Json.createReader(new StringReader(r.getBodyAsString()));
                    return reader.readObject();
                })
                .filter(j -> j.getString("order").equals(orderId))
                .forEach(r -> {
                    assertThat(r.getString("type")).isEqualTo(order.type.toUpperCase());
                    assertThat(r.getString("origin")).isEqualTo(order.origin.toUpperCase());
                    assertThat(r.getString("status")).isIn("PREPARING", "FINISHED");
                });
    }
}

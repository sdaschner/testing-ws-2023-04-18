package com.sebastian_daschner.coffee_shop.backend;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

// don't use such an approach
// it includes too many concerns and ends up in
// too much effort to write & maintain
// -> use delegation & abstraction instead, see CreateOrderTest
class CreateOrderNaiveTest {

    private Client client;
    private WebTarget ordersTarget;

    @BeforeEach
    void setUp() {
        client = ClientBuilder.newClient();
        ordersTarget = client.target(buildUri());
    }

    private URI buildUri() {
        String host = System.getProperty("coffee-shop.test.host", "localhost");
        String port = System.getProperty("coffee-shop.test.port", "8001");
        return UriBuilder.fromUri("http://{host}:{port}/orders")
                .build(host, port);
    }

    @Test
    void test() {
        // create coffee order
        //   with Espresso
        //   from Colombia
        // save <orderId>

        // verify coffee order <orderId>
        //   has Espresso
        //   from Colombia

        // verify that <orderId> is contained in all orders
    }

    @Test
    void createVerifyOrder() {
        Order order = new Order("Espresso", "Colombia");
        JsonObject requestBody = createJson(order);

        Response response = ordersTarget.request().post(Entity.json(requestBody));

        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)
            throw new AssertionError("Status was not successful: " + response.getStatus());

        URI orderUri = response.getLocation();

        Order loadedOrder = client.target(orderUri)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Order.class);

        assertThat(loadedOrder.type).isEqualTo(order.type);
        assertThat(loadedOrder.origin).isEqualTo(order.origin);

        List<URI> orders = ordersTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonArray.class).getValuesAs(JsonObject.class).stream()
                .map(o -> o.getString("_self"))
                .map(URI::create)
                .collect(Collectors.toList());

        assertThat(orders).contains(orderUri);
    }

    JsonObject createJson(Order order) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        if (order.type != null)
            builder.add("type", order.type);
        else
            builder.addNull("type");
        if (order.origin != null)
            builder.add("origin", order.origin);
        else
            builder.addNull("origin");

        return builder.build();
    }

}

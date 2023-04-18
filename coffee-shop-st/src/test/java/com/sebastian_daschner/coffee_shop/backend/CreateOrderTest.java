package com.sebastian_daschner.coffee_shop.backend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreateOrderTest {

    private final CoffeeShop coffeeShop = new CoffeeShop();
    private final Barista barista = new Barista();

    @Test
    void testCreateOrder() {
        URI orderId = coffeeShop.createCoffeeOrder(new Order("Espresso", "Colombia"));

        Order order = coffeeShop.retrieveOrder(orderId);
        assertThat(order.type).isEqualTo("Espresso");
        assertThat(order.origin).isEqualTo("Colombia");

        List<URI> orderIds = coffeeShop.retrieveOrders();
        assertThat(orderIds).contains(orderId);
    }

    @Test
    @Disabled
    void testCreateOrderVerifyStatusUpdate() {
        URI orderId = coffeeShop.createCoffeeOrder(new Order("Espresso", "Colombia"));

        Order order = coffeeShop.retrieveOrder(orderId);
        assertThat(order.status).isEqualTo("Preparing");

        barista.answerForOrder(orderId, "FINISHED");
        barista.waitForInvocation(orderId, "PREPARING");
        order = coffeeShop.retrieveOrder(orderId);
        assertThat(order.status).isEqualTo("Finished");

        barista.answerForOrder(orderId, "COLLECTED");
        barista.waitForInvocation(orderId, "FINISHED");
        order = coffeeShop.retrieveOrder(orderId);
        assertThat(order.status).isEqualTo("Collected");
    }

    @AfterEach
    void tearDown() {
        coffeeShop.close();
    }

}

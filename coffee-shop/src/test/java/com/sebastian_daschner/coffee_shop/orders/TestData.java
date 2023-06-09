package com.sebastian_daschner.coffee_shop.orders;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public final class TestData {

    public static List<Order> testOrders() {
        Order order1 = new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Colombia"));
        Order order2 = new Order(UUID.randomUUID(), CoffeeType.LATTE, new Origin("Ethiopia"));
        Order order3 = new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Ethiopia"));
        return List.of(order1, order2, order3);
    }

    public static Order testOrder() {
        return new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Colombia"));
    }

//    @Test
    void example() {
        Order order = new Order(UUID.randomUUID(), CoffeeType.LATTE, new Origin());
        OrderAssert.assertThat(order).containsMilk();
    }

}

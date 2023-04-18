package com.sebastian_daschner.coffee_shop.orders;

import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import org.assertj.core.api.AbstractAssert;

public class OrderAssert extends AbstractAssert<OrderAssert, Order> {

    public OrderAssert(Order order) {
        super(order, OrderAssert.class);
    }

    public static OrderAssert assertThat(Order order) {
        return new OrderAssert(order);
    }

    public OrderAssert containsMilk() {
        isNotNull();
        if (actual.getType() != CoffeeType.LATTE) {
            failWithMessage("Expected coffee containing milk but got %s", actual.getType());
        }
        return this;
    }

    public OrderAssert doesNotContainMilk() {
        isNotNull();
        if (actual.getType() == CoffeeType.LATTE) {
            failWithMessage("Expected coffee containing milk but got %s", actual.getType());
        }
        return this;
    }

    public OrderAssert isEspresso() {
        isNotNull();
        if (actual.getType() == CoffeeType.ESPRESSO) {
            failWithMessage("Expected espresso order but got %s", actual.getType());
        }
        return this;
    }

    public OrderAssert isFrom(String origin) {
        isNotNull();
        if (!origin.equals(actual.getOrigin().getName())) {
            failWithMessage("Expected coffee from origin %s but got %s", origin, actual.getOrigin().getName());
        }
        return this;
    }

}

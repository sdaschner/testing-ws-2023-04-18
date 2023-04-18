package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.TestData;
import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessorTD;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CoffeeShopUseCaseTest {

    private CoffeeShopTD coffeeShop;
    private OrderProcessorTD orderProcessor;

    @BeforeEach
    void setUp() {
        orderProcessor = new OrderProcessorTD();
        coffeeShop = new CoffeeShopTD(orderProcessor);
    }

    @Test
    void testCreateOrder() {
        Order order = TestData.testOrder();
        coffeeShop.createOrder(order);
        coffeeShop.verifyCreateOrder(order);
    }

    @Test
    void testProcessUnfinishedOrders() {
        List<Order> orders = TestData.testOrders();
        coffeeShop.prepareProcessUnfinishedOrders(orders);

        coffeeShop.processUnfinishedOrders();

        coffeeShop.verifyProcessUnfinishedOrders(orders);
    }

}
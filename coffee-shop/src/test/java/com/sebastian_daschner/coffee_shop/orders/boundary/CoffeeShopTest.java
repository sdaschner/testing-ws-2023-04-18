package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessor;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class CoffeeShopTest {

    private CoffeeShop coffeeShop;

    @BeforeEach
    void setUp() {
        coffeeShop = new CoffeeShop();
        coffeeShop.orderRepository = mock(OrderRepository.class);
        coffeeShop.orderProcessor = mock(OrderProcessor.class);
    }

    @Test
    void process_unfinished_orders_should_work() {
        Order order1 = new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Colombia"));
        Order order2 = new Order(UUID.randomUUID(), CoffeeType.LATTE, new Origin("Ethiopia"));
        Order order3 = new Order(UUID.randomUUID(), CoffeeType.ESPRESSO, new Origin("Ethiopia"));
        List<Order> testOrders = List.of(order1, order2, order3);
        when(coffeeShop.orderRepository.listUnfinishedOrders()).thenReturn(testOrders);

        coffeeShop.processUnfinishedOrders();

//        Mockito.verify(coffeeShop.orderProcessor, times(3)).processOrder(any());
        verify(coffeeShop.orderProcessor).processOrder(order1);
        verify(coffeeShop.orderProcessor).processOrder(order2);
        verify(coffeeShop.orderProcessor).processOrder(order3);
        verifyNoMoreInteractions(coffeeShop.orderProcessor);
    }

}
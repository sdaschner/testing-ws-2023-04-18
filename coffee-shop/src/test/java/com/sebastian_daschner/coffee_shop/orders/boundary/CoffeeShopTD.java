package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.control.OrderProcessorTD;
import com.sebastian_daschner.coffee_shop.orders.control.OrderRepository;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;

import java.util.List;

import static org.mockito.Mockito.*;

public class CoffeeShopTD extends CoffeeShop {

    private final OrderProcessorTD orderProcessorTD;

    public CoffeeShopTD(OrderProcessorTD orderProcessor) {
        this.orderProcessorTD = orderProcessor;
        this.orderProcessor = orderProcessorTD;
        this.orderRepository = mock(OrderRepository.class);
    }

    public void prepareProcessUnfinishedOrders(List<Order> orders) {
        when(orderRepository.listUnfinishedOrders()).thenReturn(orders);
        orderProcessorTD.prepareProcessUnfinishedOrders(orders);
    }

    public void verifyProcessUnfinishedOrders(List<Order> orders) {
        orderProcessorTD.verifyProcessUnfinishedOrders(orders);
    }

    public void verifyCreateOrder(Order order) {
        verify(orderRepository).persist(order);
    }
}

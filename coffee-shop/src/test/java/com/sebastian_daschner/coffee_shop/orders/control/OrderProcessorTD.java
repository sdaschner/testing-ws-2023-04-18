package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderProcessorTD extends OrderProcessor {

    public OrderProcessorTD() {
        this.orderRepository = mock(OrderRepository.class);
        this.barista = mock(Barista.class);
    }

    public void prepareProcessUnfinishedOrders(List<Order> orders) {
        when(orderRepository.findById(any())).then(invocation -> orders.stream()
                .filter(o -> o.getId().equals(invocation.getArgument(0)))
                .findAny().orElse(null));

        when(barista.retrieveOrderStatus(any())).thenReturn(OrderStatus.FINISHED);
    }

    @Override
    public void processOrder(Order order) {
//        when(orderRepository.findById(order.getId())).thenReturn(order);
        super.processOrder(order);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.FINISHED);
    }

    public void verifyProcessUnfinishedOrders(List<Order> orders) {
        verify(orderRepository, times(orders.size())).findById(any());

        orders.forEach(order -> verify(barista).retrieveOrderStatus(order));
        verifyNoMoreInteractions(barista);
        verifyNoMoreInteractions(orderRepository);
    }
}

package com.sebastian_daschner.coffee_shop.orders.boundary;

import com.sebastian_daschner.coffee_shop.orders.TestData;
import com.sebastian_daschner.coffee_shop.orders.control.Barista;
import com.sebastian_daschner.coffee_shop.orders.entity.Order;
import com.sebastian_daschner.coffee_shop.orders.entity.OrderStatus;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@Disabled
public class CoffeeShopQuarkusTest {

    @Inject
    CoffeeShop coffeeShop;

//    @InjectMock
//    Barista barista;

    @Test
    void test() {
//        Mockito.when(barista.retrieveOrderStatus(ArgumentMatchers.any())).thenReturn(OrderStatus.COLLECTED);

//        assertThat(barista.retrieveOrderStatus(new Order())).isEqualTo(OrderStatus.COLLECTED);

        coffeeShop.createOrder(TestData.testOrder());
    }

}

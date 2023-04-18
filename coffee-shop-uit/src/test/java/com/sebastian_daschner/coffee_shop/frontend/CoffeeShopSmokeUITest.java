package com.sebastian_daschner.coffee_shop.frontend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoffeeShopSmokeUITest {

    private final CoffeeShopUI coffeeShop = new CoffeeShopUI();

    @BeforeEach
    void setUp() {
        coffeeShop.init();
    }

    @Test
    void index_view_page_header_empty_table() {
        IndexView index = coffeeShop.index();
        index.assertPageHeader("Coffee orders");
    }

}

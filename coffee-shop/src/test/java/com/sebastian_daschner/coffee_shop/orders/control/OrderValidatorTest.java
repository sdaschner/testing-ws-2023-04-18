package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.boundary.CoffeeShop;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.ConstraintValidatorContext;

import java.io.StringReader;
import java.util.EnumSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderValidatorTest {

    private OrderValidator validator;

    @BeforeEach
    void setUp() {
        validator = new OrderValidator();
        validator.coffeeShop = mock(CoffeeShop.class);
        Set<CoffeeType> types = EnumSet.allOf(CoffeeType.class);
        when(validator.coffeeShop.getCoffeeTypes()).thenReturn(types);
        when(validator.coffeeShop.getOrigin(anyString())).then(invocation -> {
            String name = invocation.getArgument(0);
            Origin origin = mock(Origin.class);
            when(origin.getName()).thenReturn(name);
            when(origin.getCoffeeTypes()).thenReturn(types);
            return origin;
        });
    }

    @Test
    void testIsNotValidOnEmptyJson() {
        JsonObject json = Json.createObjectBuilder().build();
        boolean valid = validator.isValid(json, mock(ConstraintValidatorContext.class));
        assertThat(valid).isFalse();
    }

    @Test
    void testIsValid() {
        isValidForJson("Espresso", "Colombia", true);
    }

    @Test
    void testIsNotValidForIllegalType() {
        isValidForJson("Cappuccino", "Colombia", false);
    }

    @Test
    void testIsNotValidForIllegalType2() {
        isValidForJson("Cappuccino2", "Colombia", false);
    }

    private void isValidForJson(String type, String origin, boolean expected) {
        JsonObject json = Json.createReader(new StringReader("""
                        {
                            "type": "$type",
                            "origin": "$origin"
                        }
                        """.replace("$type", type).replace("$origin", origin))).readObject();
        boolean valid = validator.isValid(json, mock(ConstraintValidatorContext.class));
        assertThat(valid).isEqualTo(expected);
    }

}
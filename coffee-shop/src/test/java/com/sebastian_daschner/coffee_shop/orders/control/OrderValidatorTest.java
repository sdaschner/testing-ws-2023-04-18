package com.sebastian_daschner.coffee_shop.orders.control;

import com.sebastian_daschner.coffee_shop.orders.boundary.CoffeeShop;
import com.sebastian_daschner.coffee_shop.orders.entity.CoffeeType;
import com.sebastian_daschner.coffee_shop.orders.entity.Origin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.ConstraintValidatorContext;
import java.io.StringReader;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
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

    @ParameterizedTest
    @MethodSource("validData")
    void isValidForJson(String type, String origin) {
        JsonObject json = Json.createReader(new StringReader("""
                {
                    "type": "$type",
                    "origin": "$origin"
                }
                """.replace("$type", type).replace("$origin", origin))).readObject();
        boolean valid = validator.isValid(json, mock(ConstraintValidatorContext.class));
        assertThat(valid).isTrue();
    }

    public static Collection<String[]> validData() {
        return List.of(new String[]{"Espresso", "Colombia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"},
                new String[]{"Espresso", "Ethiopia"}
        );
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    void test(String json) {
        JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
        boolean valid = validator.isValid(jsonObject, mock(ConstraintValidatorContext.class));
        assertThat(valid).isFalse();
    }

    public static Collection<String> invalidData() {
        return List.of(
                """
                        {}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """,
                """
                        {"type":"Espresso"}
                        """
                );
    }

}
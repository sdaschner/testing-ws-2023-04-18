package com.sebastian_daschner.coffee_shop.frontend;

public class Order {

    public String type;
    public String origin;
    public String status;

    public Order() {
    }

    public Order(String type, String origin) {
        this.type = type;
        this.origin = origin;
    }

    public Order(String type, String origin, String status) {
        this.type = type;
        this.origin = origin;
        this.status = status;
    }
}

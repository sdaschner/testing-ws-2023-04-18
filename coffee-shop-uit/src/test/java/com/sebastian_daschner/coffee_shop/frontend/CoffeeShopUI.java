package com.sebastian_daschner.coffee_shop.frontend;

import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CoffeeShopUI {

    public void init() {
//        Configuration.baseUrl = uriBuilder().toString();
        open(uri() + "index.html");
        // this is how to add cookies:
        getWebDriver().manage().addCookie(new Cookie("session", "123"));
    }

    private String uri() {
        String host = System.getProperty("test.coffee-shop.host", "localhost");
        String port = System.getProperty("test.coffee-shop.port", "8001");
        return String.format("http://%s:%s/", host, port);
    }

    public IndexView index() {
        open(uri() + "index.html");
        return new IndexView();
    }

}

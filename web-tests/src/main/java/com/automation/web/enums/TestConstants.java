package com.automation.web.enums;

public class TestConstants {
    public static final class ErrorMessages {
        public static final String LOCKED_OUT = "Epic sadface: Sorry, this user has been locked out.";
        public static final String INVALID_CREDENTIALS = "Epic sadface: Username and password do not match any user in this service";
        public static final String MISSING_USERNAME = "Epic sadface: Username is required";
        public static final String MISSING_PASSWORD = "Epic sadface: Password is required";
    }

    public static final class Timeouts {
        public static final int STANDARD_TIMEOUT = 10;
        public static final int EXTENDED_TIMEOUT = 30;
        public static final int PERFORMANCE_GLITCH_TIMEOUT = 15;
    }

    public static final class ExpectedData {
        public static final String INVENTORY_TITLE = "Products";
        public static final String DEFAULT_SORT = "Name (A to Z)";

        public static final class Products {
            public static final String BACKPACK = "Sauce Labs Backpack";
            public static final String BIKE_LIGHT = "Sauce Labs Bike Light";
            public static final String BOLT_SHIRT = "Sauce Labs Bolt T-Shirt";
            public static final String FLEECE_JACKET = "Sauce Labs Fleece Jacket";
            public static final String ONESIE = "Sauce Labs Onesie";
            public static final String TEST_SHIRT = "Test.allTheThings() T-Shirt (Red)";
        }
    }
}
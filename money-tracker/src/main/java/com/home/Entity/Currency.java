package com.home.Entity;

public enum Currency {

    USD,
    EUR,
    PLN,
    GBP,
    UNKNOWN;

    public static boolean contains(String test) {

        for (Currency c : Currency.values()) {
            if (c.toString().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

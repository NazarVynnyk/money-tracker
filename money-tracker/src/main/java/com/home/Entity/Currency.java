package com.home.Entity;

/**
 * This Currency class contains information about currencies that are correct for this application
 *
 * @author Nazar Vynnyk
 */

public enum Currency {

    USD,
    EUR,
    PLN,
    GBP,
    UNKNOWN;

    /**
     * Returns <tt>true</tt> if this enum contains the specified string value
     *
     * @param test - string whose presence in this enum is to be tested
     * @return - <tt>true</tt> if this enum contains incoming value
     */
    public static boolean contains(String test) {
        for (Currency c : Currency.values()) {
            if (c.toString().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

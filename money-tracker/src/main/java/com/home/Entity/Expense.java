package com.home.Entity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Expense {

    private String name;
    private Enum<Currency> currency;
    private Double amount;

    public Expense(Enum<Currency> currency, Double amount, String name) {
        this.currency = currency;
        this.amount = amount;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(Enum<Currency> currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat();

        DecimalFormatSymbols dfs = format.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(dfs);
        format.setDecimalSeparatorAlwaysShown(false);
        return String.format("%s %s %s", name, format.format(amount), currency);
    }
}

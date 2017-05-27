package com.home.Tracker;

import com.home.Entity.Currency;
import com.home.Entity.Expense;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.home.Tracker.CurrenncyRate.convert;

public class ExpenceManager {

    private static final Logger LOGGER = Logger.getLogger(ExpenceManager.class);
    private static final Map<Date, List<Expense>> allExpenses = new TreeMap<>();


    public static void addExpense(String newExpense) {

        String[] incoming = newExpense.split(" ");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = getDate(incoming[1]);

        try {

            Double amount = Double.valueOf(incoming[2]);
            Enum<Currency> currency = Currency.UNKNOWN;
            if (Currency.contains(incoming[3])) {
                currency = Currency.valueOf(incoming[3]);
            }

            String name = incoming[4];
            if (name.charAt(0) == (char) 8220) {
                int begin = newExpense.indexOf(8220) + 1;
                int end = newExpense.lastIndexOf(8221);
                name = newExpense.substring(begin, end);
            }

            Expense expense = new Expense(currency, amount, name);

            if (date != null && !allExpenses.containsKey(date)) {
                List<Expense> expenses = new ArrayList<>();
                expenses.add(expense);
                allExpenses.put(date, expenses);
            } else if (date != null) {
                List<Expense> expenses = allExpenses.get(date);
                expenses.add(expense);
                allExpenses.put(date, expenses);
            }
        } catch (
                Exception e) {
            LOGGER.info(Arrays.toString(e.getStackTrace()));
        }

        list();
    }

    public static void list() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println();
        for (Map.Entry<Date, List<Expense>> entry : allExpenses.entrySet()) {
            System.out.println(formatter.format(entry.getKey()));
            for (Expense expense : entry.getValue()) {
                System.out.println(expense);
            }
            System.out.println();
        }
    }

    public static void clear(String expenseDate) {
        Date date = getDate(expenseDate);

        if (date != null && allExpenses.containsKey(date)) {
            allExpenses.remove(date);
        }
        list();
    }

    private static Date getDate(String expenseDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = formatter.parse(expenseDate);
            return newDate;
        } catch (ParseException e) {
            LOGGER.info(Arrays.toString(e.getStackTrace()));
        }

        return newDate;
    }

    private static Map<Currency, Double> getCurrencies() {

        List<Expense> expenses = allExpenses.values().stream().flatMap(expenses1 -> expenses1.stream()).collect(Collectors.toList());
        Map<Currency, Double> map = new HashMap<>();
        for (Expense e : expenses) {
            Currency currency = (Currency) e.getCurrency();
            if (map.containsKey(currency)) {
                map.put(currency, map.get(currency) + e.getAmount());
            } else {
                map.put(currency, e.getAmount());
            }
        }
        return map;
    }

    public static void total(String cur) {

        double spendMoney = 0.0;
        if (Currency.contains(cur)) {
            Map<Currency, Double> currencies = getCurrencies();

            for (Map.Entry<Currency, Double> entry : currencies.entrySet()) {
                String fromCurrency = entry.getKey().name();
                double conversionRate;
                if (fromCurrency.equals(cur)) {
                    conversionRate = 1.0;
                } else conversionRate = convert(fromCurrency, cur);
                double amount = entry.getValue();
                spendMoney += conversionRate * amount;
            }
        }
        System.out.println(String.format("%.2f %s", spendMoney, cur));
    }

}




package com.home.Tracker;

import com.home.Entity.Currency;
import com.home.Entity.Expense;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * This ExpenseManager class contains methods for adding, deleting and getting expenses to application? and starting application
 *
 * @author Nazar Vynnyk
 */

public class ExpenseManager {

    private static final Logger LOGGER = Logger.getLogger(ExpenseManager.class);
    private static final Map<Date, List<Expense>> allExpenses = new TreeMap<>();
    private CurrencyRate currencyRate = new CurrencyRate();

    /**
     * Starts expenses management application thru processing incoming string
     */
    public void startMoneyTracker() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.println("Please enter command (to continue enter - out)");

                    String fromConsole = scanner.nextLine();
                    String[] incoming = fromConsole.split(" ");

                    switch (incoming[0]) {
                        case ("add"):
                            addExpense(fromConsole);
                            break;
                        case ("list"):
                            list();
                            break;
                        case ("clear"):
                            clear(incoming[1]);
                            break;
                        case ("total"):
                            total(incoming[1]);
                            break;
                        case ("out"):
                            System.exit(0);
                            break;
                        default:
                            System.out.println("not valid command");
                            break;
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Adds expense object to the list of user expenses and prints all existing expenses
     *
     * @param newExpense - string with command name and string value of expense
     */
    public void addExpense(String newExpense) {
        String[] incoming = newExpense.split(" ");
        Date date = getDate(incoming[1]);
        Expense expense = createExpense(newExpense);

        if (date != null && expense != null && !allExpenses.containsKey(date)) {
            List<Expense> expenses = new ArrayList<>();
            expenses.add(expense);
            allExpenses.put(date, expenses);

        } else if (date != null && expense != null) {
            List<Expense> expenses = allExpenses.get(date);
            expenses.add(expense);
            allExpenses.put(date, expenses);

        } else {
            LOGGER.error("bead incoming command for adding expense");
            System.out.println("bead incoming command for adding expense");
        }
        list();
    }

    /**
     * Creates new expense. Is used in addExpense method
     *
     * @param newExpense - string with adding command name and string value of expense
     * @return new expense or null if incoming parameters are not correct
     */
    private Expense createExpense(String newExpense) {
        String[] incoming = newExpense.split(" ");
        Expense expense = null;
        if (incoming.length >= 5) {
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
                expense = new Expense(currency, amount, name);

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

        } else LOGGER.error("bead incoming command for adding expense");

        return expense;
    }

    /**
     * Shows the list of all expenses sorted by date
     */
    public void list() {
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

    /**
     * Removes all expenses for specified date
     *
     * @param expenseDate - date for which all expenses will be removed
     */
    public void clear(String expenseDate) {
        Date date = getDate(expenseDate);

        if (date != null && allExpenses.containsKey(date)) {
            allExpenses.remove(date);
        }
        list();
    }

    /**
     * Formats date for the given pattern "yyyy-MM-dd"
     *
     * @param expenseDate - the incoming date
     * @return formatted date
     */
    private Date getDate(String expenseDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(expenseDate);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Returns map of currencies and amount of money that was spent in each of this currencies
     *
     * @return map where currency is key and double amount - value
     */
    private Map<Currency, Double> getCurrencies() {
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

    /**
     * Calculate the total amount of money spent and present it in specified currency
     *
     * @param cur - currency in which total amount of spend money for expenses should be presented
     */
    public void total(String cur) {
        double spendMoney = 0.0;
        if (Currency.contains(cur)) {
            Map<Currency, Double> currencies = getCurrencies();

            for (Map.Entry<Currency, Double> entry : currencies.entrySet()) {
                String fromCurrency = entry.getKey().name();
                double conversionRate;
                if (fromCurrency.equals(cur)) {
                    conversionRate = 1.0;
                } else conversionRate = currencyRate.convert(fromCurrency, cur);
                double amount = entry.getValue();
                spendMoney += conversionRate * amount;
            }
        }
        System.out.println();
        System.out.println(String.format("%.2f %s", spendMoney, cur));
    }

}
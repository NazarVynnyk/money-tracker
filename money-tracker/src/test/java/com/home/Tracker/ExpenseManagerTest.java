package com.home.Tracker;

import com.home.Entity.Currency;
import com.home.Entity.Expense;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nazar Vynnyk
 */

@RunWith(MockitoJUnitRunner.class)
public class ExpenseManagerTest {

    @Mock
    private CurrencyRate currencyRate;
    @Mock
    private ExpenseManager expenseManager;


    @Before
    public void setup() {
        currencyRate = new CurrencyRate();
        expenseManager = new ExpenseManager();
    }

    @Test
    public void addExpenseBecomesOneExpense() throws Exception {
        Date date = getDate("2017-04-25");

        expenseManager.addExpense("add 2017-04-25 12 USD Jogurt");

        assertEquals(feelingExpenseMapWithOnePar(), expenseManager.allExpenses);
    }

    @Test
    public void addExpenseBecomesExistingDate() throws Exception {
        Date date = getDate("2017-04-25");

        expenseManager.allExpenses = feelingExpenseMapWithTwoPars();
        expenseManager.addExpense("add 2017-04-25 12 USD Jogurt");

        Map <Date, List <Expense>> expected = feelingExpenseMapWithTwoPars();
        List <Expense> expenses = expected.get(date);
        expenses.add(new Expense(Currency.USD, 12.0, "Jogurt"));
        expected.put(date, expenses);

        assertEquals(expected, expenseManager.allExpenses);

    }

    @Test(expected = NullPointerException.class)
    public void addExpenseBecomesNull() throws ParseException {
        Date date = expenseManager.getDate(null);

        expenseManager.addExpense("add 2017-04-25 12 USD Jogurt");

        Map <Date, List <Expense>> expected = new TreeMap <>();
        List <Expense> expensesFirst = new ArrayList <>();
        expensesFirst.add(new Expense(Currency.USD, 12.0, "Jogurt"));

        expected.put(date, expensesFirst);
    }

    @Test
    public void createsExpense() {
        Expense expected = new Expense(Currency.USD, 12.0, "Jogurt");
        Expense actual = expenseManager.createExpense("add 2017-04-25 12 USD Jogurt");
        assertEquals(expected, actual);
    }

    @Test
    public void createsExpenseReturnNull() {
        Expense actual = expenseManager.createExpense("add 2017-04-25 ");
        assertEquals(null, actual);
    }

    @Test
    public void getsDate() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date expected = formatter.parse("2017-04-25");

        assertEquals(expected, expenseManager.getDate("2017-04-25"));
    }

    @Test
    public void getCurrencies() throws Exception {
        expenseManager.allExpenses = feelingExpenseMapWithTwoPars();
        Map <Currency, Double> expected = new HashMap <>();
        expected.put(Currency.USD, 24.0);
        expected.put(Currency.EUR, 27.0);

        assertEquals(expected, expenseManager.getCurrencies());
    }

    @Test
    public void clearWorksGood() throws Exception {
        Map <Date, List <Expense>> expected = new TreeMap <>();
        List <Expense> expensesFirst = new ArrayList <>();
        expensesFirst.add(new Expense(Currency.USD, 12.0, "Jogurt"));
        expensesFirst.add(new Expense(Currency.EUR, 13.5, "Beer"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFirst = formatter.parse("2017-04-25");
        Date dateSecond = formatter.parse("2017-08-29");

        expected.put(dateFirst, expensesFirst);
        expenseManager.allExpenses = feelingExpenseMapWithTwoPars();
        expenseManager.clear("2017-08-29");

        assertEquals(expected, expenseManager.allExpenses);
    }

    public Map <Date, List <Expense>> feelingExpenseMapWithTwoPars() throws Exception {
        Map <Date, List <Expense>> actual = new TreeMap <>();
        List <Expense> expensesFirst = new ArrayList <>();
        expensesFirst.add(new Expense(Currency.USD, 12.0, "Jogurt"));
        expensesFirst.add(new Expense(Currency.EUR, 13.5, "Beer"));

        List <Expense> expensesSecond = new ArrayList <>();
        expensesSecond.add(new Expense(Currency.USD, 12.0, "Jogurt"));
        expensesSecond.add(new Expense(Currency.EUR, 13.5, "Beer"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFirst = formatter.parse("2017-04-25");
        Date dateSecond = formatter.parse("2017-08-29");

        actual.put(dateFirst, expensesFirst);
        actual.put(dateSecond, expensesSecond);
        return actual;
    }

    public Map <Date, List <Expense>> feelingExpenseMapWithOnePar() throws Exception {
        Map <Date, List <Expense>> expected = new TreeMap <>();
        List <Expense> expensesFirst = new ArrayList <>();
        expensesFirst.add(new Expense(Currency.USD, 12.0, "Jogurt"));

        Date date = getDate("2017-04-25");
        expected.put(date, expensesFirst);

        return expected;
    }

    public Date getDate(String expenseDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(expenseDate);
    }


//    @Test
//    public void totalReturnsAmount() throws Exception {
//       when(expenseManager.currencyRate.convert("USD", "EUR")).thenReturn(1.4);
//        expenseManager.allExpenses = feelingExpenseMapWithTwoPars();
////       String expected ="\r48,39 EUR";
//
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
////
//        expenseManager.total("EUR");
//        String expectedOutput = System.lineSeparator() + "48,39 EUR";
//        String actualOutput = outContent.toString("UTF-8");
//        assertEquals(expectedOutput, outContent.toString());
//    }


//    @Test
//    public void startMoneyTrackerRunsAdd () {
//        String[] incoming = "add 2017-04-27 4.75 EUR Beer".split(" ");
//       when(expenseManager.getIncomingString()).thenReturn(incoming);
//             expenseManager.startMoneyTracker();
//
//        verify(expenseManager, times(1)).addExpense(anyString());
//        verifyNoMoreInteractions(expenseManager);
//    }

//    @Test
//    public void listWorksGood() throws Exception {
//
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        List <Expense> expensesFirst = new ArrayList <>();
//        expensesFirst.add(new Expense(Currency.USD, 12.0, "Jogurt"));
//        expensesFirst.add(new Expense(Currency.EUR, 13.5, "Beer"));
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date dateFirst = formatter.parse("2017-04-25");
//
//        expenseManager.allExpenses.put(dateFirst, expensesFirst);
//        expenseManager.list();
//        String expectedOutput = System.lineSeparator() + "2017-04-25\n" + "Jogurt 12 USD\n" + "Beer 13.5 EUR\n"
//                + System.lineSeparator();
//        String actualOutput = outContent.toString("UTF-8");
//        assertEquals(expectedOutput, outContent.toString());
//    }


}
package com.home.Tracker;

import com.home.Entity.Currency;
import com.home.Entity.Expense;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.hamcrest.collection.IsMapContaining;

/**
 * Created by Nazar Vynnyk
 */

@RunWith(MockitoJUnitRunner.class)
public class ExpenseManagerTest {

    @Mock
    private CurrencyRate currencyRate;
    private ExpenseManager expenseManager;

    @Before
    public void setup() {
        currencyRate = new CurrencyRate();
        expenseManager = new ExpenseManager();
    }


//    @Test
//    public void testList() throws Exception {
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
//                +System.lineSeparator();
//        String actualOutput = outContent.toString("UTF-8");
//        assertEquals(expectedOutput,actualOutput);
//    }

    @Test
    public void testClear() throws Exception {
        Map <Date, List <Expense>> expected = new TreeMap <>();
        List <Expense> expensesFirst = new ArrayList <>();
        expensesFirst.add(new Expense(Currency.USD, 12.0, "Jogurt"));
        expensesFirst.add(new Expense(Currency.EUR, 13.5, "Beer"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFirst = formatter.parse("2017-04-25");
        Date dateSecond = formatter.parse("2017-08-29");

        expected.put(dateFirst, expensesFirst);
        expenseManager.allExpenses = feelingExpenseMap();
        expenseManager.clear("2017-08-29");

        assertEquals(expected, expenseManager.allExpenses);
    }

    public Map <Date, List <Expense>> feelingExpenseMap() throws Exception {
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
}
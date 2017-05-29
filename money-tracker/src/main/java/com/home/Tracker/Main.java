package com.home.Tracker;

/**
 * This Main class of Personal expenses management application that allows users to
 track how much money have they spent.
 *
 * @author Nazar Vynnyk
 */

public class Main {

    public static void main(String[] args){

      ExpenseManager expenseManager = new ExpenseManager();
      expenseManager.startMoneyTracker();
    }
}
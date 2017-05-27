package com.home.Tracker;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static com.home.Tracker.ExpenceManager.addExpense;
import static com.home.Tracker.ExpenceManager.clear;
import static com.home.Tracker.ExpenceManager.list;
import static com.home.Tracker.ExpenceManager.total;

public class CurrencyConvertor {

    public static void main(String[] args) throws IOException {

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

                }catch (Exception e){

                    Arrays.toString(e.getStackTrace());
                }
            }
        }
    }
}
package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) throws IOException {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0], DATE_FORMATTER);
                LocalTime time = LocalTime.parse(parts[1], TIME_FORMATTER);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                transactions.add(new Transaction(date, time, description, vendor, amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>,<time>,<vendor>,<type>,<amount>
        // For example: 2023-04-29,13:45:00,Amazon,PAYMENT,29.99
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.
    }

    private static void addDeposit(Scanner scanner) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter((FILE_NAME), true));

        System.out.println("Please enter the date in this format: (yyyy-MM-dd)");
        String iDate = scanner.nextLine();
        LocalDate fDate = LocalDate.parse(iDate, DATE_FORMATTER);
        System.out.println(fDate);

        // Prompt the user to enter the time in the specified format and parse it
        System.out.println("Please enter the time of the deposit in this format: (HH:mm:ss)");
        String iTime = scanner.nextLine();
        LocalTime fTime = LocalTime.parse(iTime, TIME_FORMATTER);
        System.out.println(fTime);

        // Prompt the user to enter the name of the description
        System.out.println("Please enter the name of the description: ");
        String description = scanner.nextLine();

        // Prompt the user to enter the name of the vendor
        System.out.println("Please enter the name of the vendor: ");
        String vendor = scanner.nextLine();

        // Prompt the user to enter the amount to deposit and validate it
        System.out.println("Please enter the amount you'd like to deposit: $");
        double depositDouble = scanner.nextDouble();
        scanner.nextLine();

        // Check if the deposit amount is negative and handle the error
        if (depositDouble < 0) {
            System.out.println("Error: You have entered an incorrect amount.");
            System.out.println("=====================================================");
        }

        Transaction deposit = new Transaction(fDate, fTime, description, vendor, depositDouble);
        transactions.add(deposit);
        writer.write(String.valueOf(deposit));
        writer.close();
        // This method should prompt the user to enter the date, time, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Deposit` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.
    }

    private static void addPayment(Scanner scanner) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter((FILE_NAME), true));

        System.out.println("Please enter the date in this format: (yyyy-MM-dd)");
        String iDate = scanner.nextLine();
        LocalDate fDate = LocalDate.parse(iDate, DATE_FORMATTER);
        System.out.println(fDate);

        // Prompt the user to enter the time in the specified format and parse it
        System.out.println("Please enter the time of the deposit in this format: (HH:mm:ss)");
        String iTime = scanner.nextLine();
        LocalTime fTime = LocalTime.parse(iTime, TIME_FORMATTER);
        System.out.println(fTime);

        // Prompt the user to enter the name of the description
        System.out.println("Please enter the name of the description: ");
        String description = scanner.nextLine();

        // Prompt the user to enter the name of the vendor
        System.out.println("Please enter the name of the vendor: ");
        String vendor = scanner.nextLine();

        // Prompt the user to enter the amount to deposit and validate it
        System.out.println("Please enter the amount you'd like to deposit: $");
        double paymentDouble = scanner.nextDouble();
        scanner.nextLine();

        // Check if the deposit amount is negative and handle the error
        if (paymentDouble < 0) {
            System.out.println("Error: You have entered an incorrect amount.");
            System.out.println("=====================================================");
        }

        Transaction payment = new Transaction(fDate, fTime,description, vendor, paymentDouble);
        transactions.add(payment);
        writer.write(String.valueOf(payment));
        writer.close();
        // This method should prompt the user to enter the date, time, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Payment` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
    }

    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, type, and amount.
    }

    private static void displayDeposits() {
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
            // This method should display a table of all deposits in the `transactions` ArrayList.
            // The table should have columns for date, time, vendor, and amount.
        }
    }

    private static void displayPayments() {
            for (Transaction transaction : transactions) {
                if (transaction.getAmount() < 0 ){
                    System.out.println(transaction);
                }
            }
        }
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, vendor, and amount.


    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    monthToDate();
                    break;
                    // Generate a report for all transactions within the current month,
                    // including the date, vendor, and amount for each transaction.
                case "2":
                    previousMonth();
                    break;
                    // Generate a report for all transactions within the previous month,
                    // including the date, vendor, and amount for each transaction.
                case "3":
                    yearToDate();
                    break;
                    // Generate a report for all transactions within the current year,
                    // including the date, vendor, and amount for each transaction.

                case "4":
                    previousYear();
                    break;
                    // Generate a report for all transactions within the previous year,
                    // including the date, vendor, and amount for each transaction.
                case "5":
                    System.out.println("Please enter the name of the vendor");
                    String vendor = scanner.nextLine();
                    filterTransactionsByVendor(vendor);
                    break;
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    private static void previousYear() {
        LocalDate previousYear = LocalDate.now().minusYears(1);
        System.out.println("All transactions in" + previousYear.getYear() + ";");
        filterTransactionsByDate(previousYear.withDayOfYear(1), previousYear.withDayOfYear(previousYear.lengthOfYear()));
        System.out.println("=================================================================");
    }

    private static void yearToDate() {
        LocalDate currentYear = LocalDate.now();
        System.out.println("All transactions in" + currentYear.getYear() + ":");
        filterTransactionsByDate(currentYear.withDayOfYear(1), currentYear);
        System.out.println("=================================================================");
    }

    private static void previousMonth() {
        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        System.out.println("All transactions in " + previousMonth.getMonth() + ":");
        filterTransactionsByDate(previousMonth.withDayOfMonth(1), previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()));
        System.out.println("=================================================================");
    }

    private static void monthToDate() {
        LocalDate currentMonth = LocalDate.now();
        System.out.println("All transactions in " + currentMonth.getMonth() + ":");
        filterTransactionsByDate(currentMonth.withDayOfMonth(1), currentMonth);
        System.out.println("=================================================================");
    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
            System.out.println("Report:");
            for (Transaction transaction : transactions) {
                if (transaction.getDate().isAfter(startDate.minusDays(1)) && transaction.getDate().isBefore(endDate.plusDays(1))) {
                    System.out.println(transaction);
                }
            }
        }
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.


    private static void filterTransactionsByVendor(String vendor) {
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equals(vendor)) {
                System.out.println(transaction);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for the vendor: " + vendor);
        }
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    }
}

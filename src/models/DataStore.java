package models;

import interfaces.DataStoreOperations;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DataStore implements DataStoreOperations {
    private static final String FILE_NAME = "resources/accounts.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Map<String, BankAccount> loadAccounts() {
        Map<String, BankAccount> accounts = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length >= 4) {
                    String cardNumber = parts[0];
                    String pinCode = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    boolean locked = Boolean.parseBoolean(parts[3]);
                    LocalDateTime lastFailedAttempt = parts.length == 5 ? LocalDateTime.parse(parts[4], FORMATTER) : null;
                    accounts.put(cardNumber, new BankAccount(cardNumber, pinCode, balance, locked,lastFailedAttempt));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        }
        return accounts;
    }

    @Override
    public void saveAccounts(Map<String, BankAccount> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (BankAccount account : accounts.values()) {
                writer.println(account.getCardNumber() + " " + account.getPinCode() + " " + account.getBalance() + " " + account.isLocked() + " " + (account.getLastFailedAttempt() != null ? account.getLastFailedAttempt().format(FORMATTER) : ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

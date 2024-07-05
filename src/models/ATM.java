package models;

import interfaces.DataStoreOperations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private static Map<String, BankAccount> accounts;
    private static Scanner scanner = new Scanner(System.in);
    private static final DataStoreOperations dataStore = new DataStore();

    public static void main(String[] args) {
        accounts = dataStore.loadAccounts();

        while (true) {
            System.out.println("Введите номер карты (формат ХХХХ-ХХХХ-ХХХХ-ХХХХ):");
            String cardNumber = scanner.nextLine();

            if (!CardValidator.isValidCardNumber(cardNumber)) {
                System.out.println("Неверный формат номера карты.");
                continue;
            }

            BankAccount account = accounts.get(cardNumber);
            if (account == null) {
                System.out.println("Карта не найдена.");
                continue;
            }

            account.unlockIfTimeElapsed();

            if (account.isLocked()) {
                System.out.println("Карта заблокирована.");
                continue;
            }

            int attempts = 0;
            while (attempts < 3) {
                System.out.println("Введите PIN-код:");
                String pinCode = scanner.nextLine();

                if (!CardValidator.isValidPinCode(pinCode)) {
                    System.out.println("Неверный PIN-код.");
                    attempts++;
                    account.incrementPinAttempts();
                    if (account.isLocked()) {
                        System.out.println("Карта заблокирована после трех неверных попыток ввода PIN-кода.");
                        dataStore.saveAccounts(accounts);
                        break;
                    }
                    continue;
                }

                if (!account.getPinCode().equals(pinCode)) {
                    System.out.println("Неверный PIN-код.");
                    attempts++;
                    account.incrementPinAttempts();
                    if (account.isLocked()) {
                        System.out.println("Карта заблокирована после трех неверных попыток ввода PIN-кода.");
                        account.setLastFailedAttempt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))));
                        dataStore.saveAccounts(accounts);
                        break;
                    }
                    continue;
                }
                account.resetPinAttempts();
                showMenu(account);
                break;
            }
        }
    }

    public static void showMenu(BankAccount account) {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Проверить баланс");
            System.out.println("2. Снять средства");
            System.out.println("3. Пополнить баланс");
            System.out.println("4. Выйти");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный код операции.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Баланс: " + account.getBalance());
                    break;
                case 2:
                    System.out.println("Введите сумму для снятия:");
                    double withdrawAmount;
                    try {
                        withdrawAmount = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат суммы для снятия.");
                        continue;
                    }
                    if (withdrawAmount <= 0) {
                        System.out.println("Неверный формат суммы для снятия.");
                        continue;
                    }
                    try {
                        account.withdraw(withdrawAmount);
                        System.out.println("Средства сняты. Текущий баланс: " + account.getBalance());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        dataStore.saveAccounts(accounts);
                    }
                    break;
                case 3:
                    System.out.println("Введите сумму для пополнения:");
                    double depositAmount;
                    try {
                        depositAmount = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат суммы для пополнения.");
                        continue;
                    }
                    if (depositAmount <= 0) {
                        System.out.println("Неверный формат суммы для пополнения.");
                        continue;
                    }
                    try {
                        account.deposit(depositAmount);
                        System.out.println("Баланс пополнен. Текущий баланс: " + account.getBalance());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        dataStore.saveAccounts(accounts);
                    }
                    break;
                case 4:
                    dataStore.saveAccounts(accounts);
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный код операции.");
            }
        }
    }
}

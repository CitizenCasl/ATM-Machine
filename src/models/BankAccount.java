package models;

import interfaces.BankAccountOperation;

import java.time.LocalDateTime;

public class BankAccount implements BankAccountOperation {
    private final String cardNumber;
    private final String pinCode;
    private double balance;
    private boolean locked;
    private int pinAttempts;
    private LocalDateTime lastFailedAttempt;

    public BankAccount(String cardNumber, String pinCode, double balance, boolean locked, LocalDateTime lastFailedAttempt) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
        this.locked = locked;
        this.pinAttempts = 0;
        this.lastFailedAttempt = lastFailedAttempt;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setLastFailedAttempt(LocalDateTime lastFailedAttempt) {
        this.lastFailedAttempt = lastFailedAttempt;
    }

    public LocalDateTime getLastFailedAttempt() {
        return lastFailedAttempt;
    }

    public void incrementPinAttempts() {
        pinAttempts++;
        if (pinAttempts >= 3) {
            locked = true;
        }
    }

    public void resetPinAttempts() {
        pinAttempts = 0;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 1000000) {
            throw new IllegalArgumentException("Сумма пополнения не должна превышать 1 000 000.");
        }
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Недостаточно средств на счете.");
        }
        balance -= amount;
    }

    @Override
    public void unlockIfTimeElapsed() {
        if (locked && lastFailedAttempt != null) {
            LocalDateTime now = LocalDateTime.now();
            if (lastFailedAttempt.plusDays(1).isBefore(now)) {
                setLocked(false);
                lastFailedAttempt = null;
                resetPinAttempts();
            }
        }
    }
}

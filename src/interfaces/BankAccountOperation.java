package interfaces;

public interface BankAccountOperation {
    void deposit(double amount);

    void withdraw(double amount);

    void unlockIfTimeElapsed();
}

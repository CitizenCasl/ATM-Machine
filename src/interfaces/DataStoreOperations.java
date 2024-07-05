package interfaces;

import models.BankAccount;

import java.util.Map;

public interface DataStoreOperations {
    Map<String, BankAccount> loadAccounts();

    void saveAccounts(Map<String, BankAccount> accounts);
}

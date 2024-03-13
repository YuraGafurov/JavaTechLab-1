package Accounts;

import Banks.Bank;
import Transactions.Transaction;

public interface Account {
    void withdrawMoney(Double amount);
    void replenish(Double amount);
    void calculatePercents();
    void payPercents();
    void deductCommission();
    Bank getBank();
    void addTransaction(Transaction transaction);
    void removeTransaction(Transaction transaction);
    void setPercent(Double percent);
    void setCommission(Double commission);
    Double getAmount();
}

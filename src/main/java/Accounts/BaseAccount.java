package Accounts;

import Banks.Bank;
import Clients.Client;
import Exceptions.AmountLessThanZeroException;
import Transactions.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseAccount implements Account {
    protected UUID id;
    protected Double amount = 0.0;
    protected Double percent = 0.0;
    protected Double amountWithPercent = 0.0;
    protected Double commission = 0.0;
    protected Client owner;
    protected Bank bank;
    protected Map<UUID, Transaction> transactionsHistory;

    public BaseAccount(UUID id, Client owner, Bank bank) {
        this.id = id;
        this.owner = owner;
        this.bank = bank;
        transactionsHistory = new HashMap<>();
    }

    @Override
    public void withdrawMoney(Double amount) {
        if(amount < 0) throw new AmountLessThanZeroException();
        this.amount -= amount;
    }

    @Override
    public void replenish(Double amount) {
        if(amount < 0) throw new AmountLessThanZeroException();
        this.amount += amount;
    }

    @Override
    public void calculatePercents() {
        amountWithPercent += amount*(percent/365);
    }

    @Override
    public void payPercents() {
        amount += amountWithPercent;
    }


    @Override
    public void deductCommission() {
        amount -=commission;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionsHistory.put(transaction.getId(), transaction);
    }

    @Override
    public void removeTransaction(Transaction transaction) {
        transactionsHistory.remove(transaction.getId());
    }
}

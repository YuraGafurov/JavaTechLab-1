package Accounts;

import Banks.Bank;
import Clients.Client;
import Exceptions.WithdawalAmountGreaterThanAccountAmountException;

import java.util.UUID;

public class DebitAccount extends BaseAccount {

    public DebitAccount(UUID id,  Double percent, Client owner, Bank bank) {
        super(id, owner, bank);
        this.percent = percent;
    }

    @Override
    public void withdrawMoney(Double amount) {
        if (amount > this.amount) throw new WithdawalAmountGreaterThanAccountAmountException();
        super.withdrawMoney(amount);
    }
}

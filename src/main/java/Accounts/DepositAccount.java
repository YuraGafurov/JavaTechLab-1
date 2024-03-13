package Accounts;

import Banks.Bank;
import Clients.Client;
import Exceptions.InvalidOperationWithDepositException;
import Exceptions.WithdawalAmountGreaterThanAccountAmountException;

import java.time.LocalDateTime;
import java.util.UUID;

public class DepositAccount extends BaseAccount {
    private final LocalDateTime accountTerm;

    public DepositAccount(
            UUID id,
            Double deposit,
            Double percent,
            LocalDateTime accountTerm,
            Client owner,
            Bank bank) {
        super(id, owner, bank);
        this.amount = deposit;
        this.percent = percent;
        this.accountTerm = accountTerm;
    }

    @Override
    public void withdrawMoney(Double amount) {
        if (amount > this.amount) throw new WithdawalAmountGreaterThanAccountAmountException();
        if (LocalDateTime.now().isBefore(accountTerm))
            throw new InvalidOperationWithDepositException("You can't withdraw money yet");
        super.withdrawMoney(amount);
    }
}

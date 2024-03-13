package Transactions;

import Accounts.Account;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WithdrawMoneyTransaction implements Transaction{
    private final UUID id;
    private final Account account;
    private final Double amount;

    public WithdrawMoneyTransaction(UUID id, Account account, Double amount) {
        this.id = id;
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        account.withdrawMoney(amount);
        account.addTransaction(this);
    }

    @Override
    public void rollback() {
        account.replenish(amount);
        account.removeTransaction(this);
    }
}

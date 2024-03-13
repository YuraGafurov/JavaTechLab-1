package Transactions;

import Accounts.Account;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TransferMoneyTransaction implements Transaction {
    private final UUID id;
    private final Account sender;
    private final Account receiver;
    private final Double amount;

    public TransferMoneyTransaction(UUID id, Account sender, Account receiver, Double amount) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
    @Override
    public void execute() {
        sender.getBank().transferMoneyToAccount(amount, sender, receiver);
        sender.addTransaction(this);
        receiver.addTransaction(this);
    }

    @Override
    public void rollback() {
        sender.getBank().transferMoneyToAccount(amount, receiver, sender);
        sender.removeTransaction(this);
        receiver.removeTransaction(this);
    }
}

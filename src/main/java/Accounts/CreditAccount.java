package Accounts;

import Banks.Bank;
import Clients.Client;
import Exceptions.OutOfCreditLimitException;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreditAccount extends BaseAccount {
    private Double creditLimit;

    public CreditAccount(UUID id, Double creditLimit, Double commission, Client owner, Bank bank) {
        super(id, owner, bank);
        this.creditLimit = creditLimit;
        this.commission = commission;
    }

    @Override
    public void withdrawMoney(Double amount) {
        if ((this.amount - amount) < -(creditLimit)) throw new OutOfCreditLimitException();
        super.withdrawMoney(amount);
    }
}

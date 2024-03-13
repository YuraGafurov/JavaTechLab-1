package Banks;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.DebitAccount;
import Accounts.DepositAccount;
import Clients.Client;
import Observer.EventManager;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class Bank {
    private final HashMap<UUID, Account> accounts;
    private String name;
    private BankTerms bankTerms;
    private final EventManager eventManager;

    public Bank(String name, BankTerms bankTerms) {
        this.name = name;
        this.bankTerms = bankTerms;
        eventManager = new EventManager();
        accounts = new HashMap<>();
    }

    public void transferMoneyToAccount(Double amount, Account sender, Account receiver) {
        if (receiver.getBank().getName().equals(getName())) {
            sender.withdrawMoney(amount);
            receiver.replenish(amount);
        } else {
            CentralBank.getInstance().transferMoneyToAnotherBank(amount, sender, receiver);
        }
    }

    public void calculatePercentsForAllAccounts() {
        accounts.values().forEach(Account::calculatePercents);
    }

    public void payPercentsForAllAccounts() {
        accounts.values().forEach(Account::payPercents);
    }

    public void deductCommissionForAllAccounts() {
        accounts.values().forEach(Account::deductCommission);
    }

    public void updateAnnualInterest(Double interest) {
        bankTerms.setAnnualInterest(interest);
        accounts.values().forEach(a -> a.setPercent(interest));
        eventManager.notify("Interest updated");
    }

    public void updateCommission(Double commission) {
        bankTerms.setCommission(commission);
        accounts.values().forEach(a -> a.setCommission(commission));
        eventManager.notify("Commission updated");
    }

    public void updateCreditLimit(Double creditLimit) {
        bankTerms.setCreditLimit(creditLimit);
        accounts.values().forEach(account -> {
            if (account instanceof CreditAccount creditAccount) {
                creditAccount.setCreditLimit(creditLimit);
            }
        });
        eventManager.notify("Credit limit updated");
    }

    public Account createDebitAccount(Client client) {
        var accountId = UUID.randomUUID();
        var account = new DebitAccount(
                accountId,
                bankTerms.getAnnualInterest(),
                client,
                this);
        accounts.put(accountId, account);

        return account;
    }

    public Account createDepositAccount(Double deposit, Client client) {
        var accountId = UUID.randomUUID();
        var account = new DepositAccount(
                accountId,
                deposit,
                bankTerms.getAnnualInterestForDeposit(deposit),
                LocalDateTime.now().plusMonths(bankTerms.getDepositAccountTermInMonths()),
                client,
                this);
        accounts.put(accountId, account);

        return account;
    }

    public Account createCreditAccount(Client client) {
        var accountId = UUID.randomUUID();
        var account = new CreditAccount(
                accountId,
                bankTerms.getCreditLimit(),
                bankTerms.getCommission(),
                client,
                this);
        accounts.put(accountId, account);

        return account;
    }

    public void deleteAccountById(UUID id) {
        accounts.remove(id);
    }
}

package Banks;

import Accounts.Account;

import java.util.ArrayList;

public class CentralBank {
    private static CentralBank instance;
    private final Double transferCommission = 0.1;
    private final ArrayList<Bank> banks;

    private CentralBank() {
        banks = new ArrayList<>();
    }

    public static CentralBank getInstance() {
        if (instance == null) instance = new CentralBank();
        return instance;
    }

    public void transferMoneyToAnotherBank(Double amount, Account sender, Account receiver) {
        sender.withdrawMoney(amount + amount * transferCommission);
        receiver.replenish(amount);
    }

    public Bank createBank(String name, BankTerms bankTerms) {
        var bank = new Bank(name, bankTerms);
        banks.add(bank);
        return bank;
    }

    public void calculateInterest() {
        banks.forEach(Bank::calculatePercentsForAllAccounts);
    }

    public void payInterest() {
        banks.forEach(Bank::payPercentsForAllAccounts);
    }

    public void deductCommission() {
        banks.forEach(Bank::deductCommissionForAllAccounts);
    }

    public void timeMachine(int days) {
        for (int i = 1; i <= days; i++) {
            calculateInterest();
            if (i % 30 == 0) {
                payInterest();
                deductCommission();
            }
        }
    }
}
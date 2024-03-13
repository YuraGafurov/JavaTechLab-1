import Accounts.Account;
import Banks.Bank;
import Banks.BankTerms;
import Banks.CentralBank;
import Clients.Client;
import Exceptions.OutOfCreditLimitException;
import Transactions.ReplenishMoneyTransaction;
import Transactions.TransferMoneyTransaction;
import Transactions.WithdrawMoneyTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class Tests {

    Map<Double, Double> annualInterestsForDeposits;
    CentralBank centralBank;
    Bank alpha;
    Bank tink;
    Client clientNekit;
    Client clientTimur;
    Account nekitDebitAccount;
    Account timurCreditAccount;

    public Tests() {
        centralBank = CentralBank.getInstance();

        annualInterestsForDeposits = new TreeMap<>();
        annualInterestsForDeposits.put(0.0, 3.0);
        annualInterestsForDeposits.put(50000.0, 3.5);
        annualInterestsForDeposits.put(100000.0, 4.0);
        BankTerms bankTerms = new BankTerms(
                10.0, 20.0, 36000.0, 3,
                5000.0, annualInterestsForDeposits);

        alpha = centralBank.createBank("Alphabank", bankTerms);
        tink = centralBank.createBank("Tinkoff", bankTerms);

    }

    @BeforeEach
    public void setUp() {
        clientNekit = Client.builder()
                .id(UUID.randomUUID())
                .name("Nekit")
                .surname("Malahov")
                .address("Kudrovo")
                .passportNumber("73210000")
                .build();
        clientTimur = Client.builder()
                .id(UUID.randomUUID())
                .name("Timur")
                .surname("Gontar")
                .address("Vyazma")
                .passportNumber("6018437165")
                .build();
        nekitDebitAccount = alpha.createDebitAccount(clientNekit);
        timurCreditAccount = alpha.createCreditAccount(clientTimur);
    }

    @Test
    public void successMoneyReplenishment() {
        var replenish = new ReplenishMoneyTransaction(UUID.randomUUID(), nekitDebitAccount, 100d);
        replenish.execute();

        var expected = 100d;
        var actual = nekitDebitAccount.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    public void successMoneyWithdrawal() {
        var replenish = new ReplenishMoneyTransaction(UUID.randomUUID(), nekitDebitAccount, 100d);
        var withdrawal = new WithdrawMoneyTransaction(UUID.randomUUID(), nekitDebitAccount, 50d);
        replenish.execute();
        withdrawal.execute();

        var expected = 50d;
        var actual = nekitDebitAccount.getAmount();;
        assertEquals(expected, actual);
    }

    @Test
    public void successMoneyTransfer() {
        var replenish = new ReplenishMoneyTransaction(UUID.randomUUID(), nekitDebitAccount, 100d);
        var transfer = new TransferMoneyTransaction(UUID.randomUUID(), nekitDebitAccount, timurCreditAccount, 50d);
        replenish.execute();
        transfer.execute();

        var expected = 50d;
        var actualNekit = nekitDebitAccount.getAmount();
        var actualTimur = timurCreditAccount.getAmount();

        assertEquals(expected, actualNekit);
        assertEquals(expected, actualTimur);
    }

    @Test
    public void invalidMoneyWithdrawalFromCreditAccount() {
        var replenish = new ReplenishMoneyTransaction(UUID.randomUUID(), timurCreditAccount, 100d);
        var withdrawal = new WithdrawMoneyTransaction(UUID.randomUUID(), timurCreditAccount, 100000d);
        replenish.execute();

        var actual = new Exception();
        try {
            withdrawal.execute();
        } catch (Exception e) {
            actual = e;
        }

        assertInstanceOf(OutOfCreditLimitException.class, actual);
    }

    @Test
    public void successCommissionDeduction() {
        var replenish = new ReplenishMoneyTransaction(UUID.randomUUID(), timurCreditAccount, 500d);
        replenish.execute();
        var centralBank = CentralBank.getInstance();
        centralBank.timeMachine(30);

        var expected = 480d;
        var actual = timurCreditAccount.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    public void successTransferToAnotherBankWithCommission() {
        var nekitDebitTinkoffAccount = tink.createDebitAccount(clientNekit);
        var replenish = new ReplenishMoneyTransaction(UUID.randomUUID(), nekitDebitAccount, 500d);
        var transfer = new TransferMoneyTransaction(UUID.randomUUID(), nekitDebitAccount, nekitDebitTinkoffAccount, 250d);
        replenish.execute();
        transfer.execute();

        var expected1 = 225d;
        var actual1 = nekitDebitAccount.getAmount();
        var expected2 = 250d;
        var actual2 = nekitDebitTinkoffAccount.getAmount();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}
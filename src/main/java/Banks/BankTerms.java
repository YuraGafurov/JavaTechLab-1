package Banks;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class BankTerms {
    private Double annualInterest;
    private Double commission;
    private Double creditLimit;
    private Integer depositAccountTermInMonths;
    private Double maxTransactionAmountForSuspiciousClientAccounts;

    private final Map<Double, Double> annualInterestsForDeposits;

    public BankTerms
            (Double annualInterest,
             Double commission,
             Double creditLimit,
             Integer depositAccountTermInMonths,
             Double maxTransactionAmountForSuspiciousClientAccounts,
             Map<Double, Double> annualInterestsForDeposits) {
        this.annualInterest = annualInterest;
        this.commission = commission;
        this.creditLimit = creditLimit;
        this.depositAccountTermInMonths = depositAccountTermInMonths;
        this.maxTransactionAmountForSuspiciousClientAccounts = maxTransactionAmountForSuspiciousClientAccounts;
        this.annualInterestsForDeposits = annualInterestsForDeposits;
    }

    public Double getAnnualInterestForDeposit(Double deposit) {
        return annualInterestsForDeposits.keySet().stream()
                .filter(dep -> dep <= deposit)
                .findFirst()
                .map(annualInterestsForDeposits::get)
                .orElse(0D);
    }
}

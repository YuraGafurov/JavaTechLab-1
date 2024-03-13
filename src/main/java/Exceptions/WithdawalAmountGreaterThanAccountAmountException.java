package Exceptions;

public class WithdawalAmountGreaterThanAccountAmountException extends IllegalArgumentException {
    public WithdawalAmountGreaterThanAccountAmountException() {
        super();
    }

    public WithdawalAmountGreaterThanAccountAmountException(String message) {
        super(message);
    }
}

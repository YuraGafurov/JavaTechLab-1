package Exceptions;

public class OutOfCreditLimitException extends IllegalArgumentException {
    public OutOfCreditLimitException() {
        super();
    }

    public OutOfCreditLimitException(String message) {
        super(message);
    }
}

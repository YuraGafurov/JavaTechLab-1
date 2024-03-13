package Exceptions;

public class InvalidOperationWithDepositException extends RuntimeException {
    public InvalidOperationWithDepositException() {
        super();
    }

    public InvalidOperationWithDepositException(String message) {
        super(message);
    }
}

package Exceptions;

public class AmountLessThanZeroException extends IllegalArgumentException {
    public AmountLessThanZeroException() {
        super();
    }

    public AmountLessThanZeroException(String message) {
        super(message);
    }
}

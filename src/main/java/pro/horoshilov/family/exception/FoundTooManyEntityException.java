package pro.horoshilov.family.exception;

public class FoundTooManyEntityException extends Exception {

    public FoundTooManyEntityException(final String message) {
        super(message);
    }

    public FoundTooManyEntityException(final String message, final Exception ex) {
        super(message, ex);
    }
}

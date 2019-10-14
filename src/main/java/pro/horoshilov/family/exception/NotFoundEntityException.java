package pro.horoshilov.family.exception;

public class NotFoundEntityException extends Exception {

    public NotFoundEntityException(final String message) {
        super(message);
    }

    public NotFoundEntityException(final String message, final Exception ex) {
        super(message, ex);
    }
}

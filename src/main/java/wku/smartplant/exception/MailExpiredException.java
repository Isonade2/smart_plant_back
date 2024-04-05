package wku.smartplant.exception;

public class MailExpiredException extends RuntimeException {
    public MailExpiredException(String message) {
        super(message);
    }
}

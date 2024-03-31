package wku.smartplant.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;


public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}

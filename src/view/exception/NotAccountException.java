package view.exception;

public class NotAccountException extends RuntimeException{

    private String message;

    public NotAccountException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

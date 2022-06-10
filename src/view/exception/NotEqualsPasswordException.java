package view.exception;

public class NotEqualsPasswordException extends RuntimeException{

    private String message;

    public NotEqualsPasswordException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

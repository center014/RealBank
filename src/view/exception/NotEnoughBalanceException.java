package view.exception;

public class NotEnoughBalanceException extends RuntimeException{

    private String message;

    public NotEnoughBalanceException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

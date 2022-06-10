package view.exception;

public class AccountNotMoneyException extends RuntimeException{

    private String message;

    public AccountNotMoneyException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

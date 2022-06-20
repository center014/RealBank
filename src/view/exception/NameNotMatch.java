package view.exception;

public class NameNotMatch extends RuntimeException{

    private String message;

    public NameNotMatch(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

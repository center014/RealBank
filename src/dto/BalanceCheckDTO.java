package dto;

public class BalanceCheckDTO {

    private int balance;

    public BalanceCheckDTO(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceCheckDTO{" +
                "[잔금]=" + balance +
                '}';
    }
}

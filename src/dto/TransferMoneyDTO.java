package dto;

public class TransferMoneyDTO {

    private String memberName;
    private int balance;
    private int money;

    public TransferMoneyDTO() {

    }

    public TransferMoneyDTO(String memberName, int money, int balance) {
        this.memberName = memberName;
        this.balance = balance;
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getMemberName() {
        return memberName;
    }

    public int getBalance() {
        return balance;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "TransferMoneyDTO{" +
                "[고객이름]='" + memberName + '\'' +
                ", [잔금]=" + balance +
                '}';
    }
}

package dto;

public class TransferDTO {

    private String memberName;
    private int balance;

    public TransferDTO() {

    }

    public TransferDTO(String memberName, int balance) {
        this.memberName = memberName;
        this.balance = balance;
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
        return "TransferDTO{" +
                "[고객이름]='" + memberName + '\'' +
                ", [잔금]=" + balance +
                '}';
    }
}

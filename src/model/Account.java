package model;

public class Account {

    public Account() {

    }

    public Account(String accountNo, String accountPassword, int balance) {
        this.accountNo = accountNo;
        this.accountPassword = accountPassword;
        this.balance = balance;
    }

    public Account(Long accountId, String accountNo, String accountPassword, int balance) {
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.accountPassword = accountPassword;
        this.balance = balance;
    }

    // 시퀀스 번호
    private Long accountId;
    // 계좌번호
    private String accountNo;
    // 계좌 비밀번호
    private String accountPassword;
    // 잔금
    private int balance;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "[시퀀스]=" + accountId +
                ", [계좌번호]='" + accountNo + '\'' +
                ", [계좌비밀번호]='" + accountPassword + '\'' +
                ", [잔금]=" + balance +
                '}';
    }

}

package view;

import dao.AccountDao;
import dao.MemberDao;
import dto.BalanceCheckDTO;
import dto.TransferMoneyDTO;
import model.Account;
import model.Member;
import util.ConnectionProvider;
import view.exception.AccountNotMoneyException;
import view.exception.NotAccountException;
import view.exception.NotEnoughBalanceException;
import view.exception.NotEqualsPasswordException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainView {

    Scanner scanner = new Scanner(System.in);
    MemberDao memberDao = MemberDao.getInstance();
    AccountDao accountDao = AccountDao.getInstance();

    public void mainMenu() {
        System.out.println("*********** Real Bank ***********");
        System.out.println("1. 회원가입.");
        System.out.println("2. 로그인.");
        System.out.println("3. 관리자 메뉴.");
        System.out.println("0. 종료.");

        int select = scanner.nextInt();
        switch (select) {
            case 1:
                joinedMember();
                break;
            case 2:
                login();
                break;
            case 3:
                break;
            case 0:
                System.out.println("종료합니다.");
                break;
        }
    }

    // (1) 회원가입
    private void joinedMember() {
        System.out.print("이름 : ");
        String memberName = scanner.next();
        System.out.print("이메일 : ");
        String email = scanner.next();
        System.out.print("비밀번호 : ");
        String memberPassword = scanner.next();
        Member member = new Member(memberName, email, memberPassword);
        Connection connection = null;
        int resultCnt = 0;
        try {
            connection = ConnectionProvider.getConnection();
            resultCnt = memberDao.joinedMember(member, connection);
            if (resultCnt > 0) {
                System.out.println("회원가입이 정상처리 되었습니다.");
            } else {
                System.out.println("회원가입에 실패하였습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mainMenu();
        }
    }

    // (2) 로그인
    private void login() {
        System.out.print("E-mail : ");
        String email = scanner.next();
        Connection connection = null;
        try {
            connection = ConnectionProvider.getConnection();
            Member member = memberDao.existByMemberEmail(email, connection);
            if (member != null) {
                System.out.print("P/W : ");
                String memberPassword = scanner.next();
                if (!member.getMemberPassword().equals(memberPassword)) {
                    System.out.println("패스워드가 일치하지 않습니다.");
                    mainMenu();
                } else {
                    connection = ConnectionProvider.getConnection();
                    memberDao.updateRecentlyLogin(member, connection);
                    System.out.println(member.getMemberName() + "님께서 로그인에 성공하셨습니다.");
                    loginMenu(member);
                }
            } else {
                System.out.println("가입된 E-mail이 아닙니다.");
                mainMenu();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 로그인 메뉴
    private void loginMenu(Member member) {
        System.out.println("===========================================");
        System.out.println("1. 통장개설.");
        System.out.println("2. 입금.");
        System.out.println("3. 출금.");
        System.out.println("4. 잔액 조회.");
        System.out.println("5. 송금.");
        System.out.println("6. 로그아웃");

        int select = scanner.nextInt();
        switch (select) {
            case 1:
                createAccount(member);
                break;
            case 2:
                deposit(member);
                break;
            case 3:
                withDraw(member);
                break;
            case 4:
                balanceCheck(member);
                break;
            case 5:
                transferMoney(member);
                break;
            case 6:
                System.out.println("로그아웃 합니다.");
                mainMenu();
                break;
        }
    }

    // (3) 통장개설
    private void createAccount(Member member) {
        System.out.println("계좌번호는 자동생성 됩니다.");
        String accountNo = UUID.randomUUID().toString();
        System.out.print("계좌 비밀번호 설정 : ");
        String accountPassword = scanner.next();
        System.out.print("계좌 번호 확인 : ");
        String confirmPassword = scanner.next();

        Connection connection = null;
        int resultCnt = 0;
        try {
            if (accountPassword.equals(confirmPassword)) {
                Account account = new Account(accountNo, accountPassword, 0);
                connection = ConnectionProvider.getConnection();
                resultCnt = AccountDao.getInstance().createAccount(member, account, connection);
            } else {
                throw new NotEqualsPasswordException("system log : Password not match..");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotEqualsPasswordException e) {
            System.out.println("패스워드가 일치하지 않습니다.");
            System.out.println(e.getMessage());
        }
        if (resultCnt > 0) {
            System.out.println("계좌를 성공적으로 생성하였습니다.");
        } else {
            System.out.println("계좌 생성에 실패하셨습니다.");
        }
        loginMenu(member);
    }

    // (4) 입금
    public void deposit(Member member) {
        Connection connection = null;
        int resultCnt = 0;
        try {
            connection = ConnectionProvider.getConnection();
            List<Account> accountList = AccountDao.getInstance().selectAccountByMember(member, connection);
            for (Account account : accountList) {
                System.out.println(account);
            }
            System.out.print("어떤 계좌로 입금하시겠습니까? : ");
            int select = scanner.nextInt();
            connection = ConnectionProvider.getConnection();
            Account account = AccountDao.getInstance().selectAccountByAccountId(select, connection);
            if (account == null) {
                throw new NotAccountException("system log : Not Account in Bank");
            }
            System.out.print("얼마를 입금하시겠습니까? : ");
            int money = scanner.nextInt();
            account.setBalance(account.getBalance() + money);
            connection = ConnectionProvider.getConnection();
            resultCnt = AccountDao.getInstance().deposit(select, account.getBalance(), connection);
            if (resultCnt > 0) {
                System.out.println(money + "원이 입금되었습니다.");
            } else {
                System.out.println("입금에 실패하셨습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotAccountException e) {
            System.out.println("해당 계좌가 없습니다.");
            System.out.println(e.getMessage());
        } finally {
            loginMenu(member);
        }
    }

    // (5) 출금
    public void withDraw(Member member) {
        Connection connection = null;
        int resultCnt = 0;
        try {
            connection = ConnectionProvider.getConnection();
            List<Account> accountList = AccountDao.getInstance().selectAccountByMember(member, connection);
            for (Account account : accountList) {
                System.out.println(account);
            }
            System.out.print("어떤 계좌에서 출금하시겠습니까? : ");
            int select = scanner.nextInt();
            connection = ConnectionProvider.getConnection();
            Account account = AccountDao.getInstance().selectAccountByAccountId(select, connection);
            try {
                if (account == null) {
                    throw new NotAccountException("system log : Not Account in Bank");
                }
                System.out.print("출금 금액 : ");
                int money = scanner.nextInt();
                if (account.getBalance() <= 0) {
                    throw new AccountNotMoneyException("system log : Account don't have money");
                }
                if (account.getBalance() < money) {
                    throw new NotEnoughBalanceException("system log : Not Enough Money in Account");
                }
                account.setBalance(account.getBalance() - money);
                connection = ConnectionProvider.getConnection();
                resultCnt = AccountDao.getInstance().withDraw(select, account.getBalance(), connection);
                if (resultCnt > 0) {
                    System.out.println(money + "원이 출금되었습니다.");
                } else {
                    System.out.println("출금에 실패하셨습니다.");
                }
            } catch (NotAccountException e) {
                System.out.println("해당 계좌가 존재하지 않습니다.");
                System.out.println(e.getMessage());
            } catch (AccountNotMoneyException e) {
                System.out.println("계좌에 금액이 충분하지 않습니다.");
                System.out.println(e.getMessage());
            } catch (NotEnoughBalanceException e) {
                System.out.println("출금 금액보다 계좌에 잔금이 부족합니다.");
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            loginMenu(member);
        }
    }

    // (6) 잔액 조회
    public void balanceCheck(Member member) {
        Connection connection = null;
        try {
            connection = ConnectionProvider.getConnection();
            List<Account> accountList = AccountDao.getInstance().selectAccountByMember(member, connection);
            for (Account account : accountList) {
                System.out.println(account);
            }
            System.out.print("어느 계좌의 잔금을 확인하시겠습니까? : ");
            int select = scanner.nextInt();
            connection = ConnectionProvider.getConnection();
            List<BalanceCheckDTO> balanceCheckDTOList = AccountDao.getInstance().balanceCheck(select, connection);
            for (BalanceCheckDTO balanceCheckDTO : balanceCheckDTOList) {
                System.out.println(balanceCheckDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            loginMenu(member);
        }
    }

    // (7) 송금
    // 계좌번호의 예금주가 일치하는지
    // A가 보내려는 금액이 통장에 잔고보다 적은지
    public void transferMoney(Member member) {
        Connection connection = null;
        int resultCntMy = 0;
        int resultCntOther = 0;
        try {
            connection = ConnectionProvider.getConnection();
            // 내 계좌가 있는지 확인.
            boolean checkAccount = AccountDao.getInstance().checkMyAccount(member.getMemberId(), connection);
            if (!checkAccount) {
                throw new NotAccountException("system log : Not Account in Bank");
            }
            System.out.print("어느 계좌에 송금하시겠습니까? : ");
            String accountNo = scanner.next();

            // 받는 사람의 계좌 존재 여부 확인.
            connection = ConnectionProvider.getConnection();
            boolean checkAccountNo = AccountDao.getInstance().checkAccountNoExist(accountNo, connection);
            if (!checkAccountNo) {
                throw new NotAccountException("system log : Not Account in Bank");
            }

            // 나의 어떤 계좌에서 돈을 송금? (계좌가 2개 이상일때)
            connection = ConnectionProvider.getConnection();
            if (accountDao.checkMyAccountCount(member.getMemberId(), connection) > 1) {
                try {
                    connection = ConnectionProvider.getConnection();
                    List<Account> accountList = AccountDao.getInstance().selectAccountByMember(member, connection);
                    for (Account account : accountList) {
                        System.out.println(account);
                    }
                    System.out.print("어떤 계좌에서 송금하시겠습니까? : ");
                    int select = scanner.nextInt();

                    connection = ConnectionProvider.getConnection();
                    Account account = AccountDao.getInstance().selectAccountByAccountId(select, connection);
                    System.out.print("얼마를 송금하시겠습니까? : ");
                    int money = scanner.nextInt();

                    // 내가 보내려는 돈이 내 계좌에 충분한지 검사.
                    if (account.getBalance() <= 0) {
                        throw new AccountNotMoneyException("system log : account don't have money");
                    }
                    if (account.getBalance() < money) {
                        throw new NotEnoughBalanceException("system log : Not enough money in account");
                    }

                    // 실제로송금 진행 ( 내 계좌 - , 받는사람 계좌 + )
                    account.setBalance(account.getBalance() - money);
                    connection = ConnectionProvider.getConnection();
                    resultCntMy = AccountDao.getInstance().withDraw(select, account.getBalance(), connection);
                    if (resultCntMy > 0) {
                        System.out.println(money + "원을 송금합니다.");
                    } else {
                        System.out.println("송금 불가");
                    }
                    // TODO 받는 사람 계좌에 금액 추가
                    connection = ConnectionProvider.getConnection();
                    resultCntOther = AccountDao.getInstance().transferWithDraw(accountNo, money, connection);
                    if (resultCntOther > 0) {
                        System.out.println(money + "원이 입금되었습니다.");
                    } else {
                        System.out.println("송금 불가.");
                    }
                } catch (AccountNotMoneyException e) {
                    System.out.println("해당 계좌에 잔금이 충분하지 않습니다.");
                    System.out.println(e.getMessage());
                } catch (NotEnoughBalanceException e) {
                    System.out.println("송금 금액보다 잔금이 적습니다.");
                    System.out.println(e.getMessage());
                }
            } else if (accountDao.checkMyAccountCount(member.getMemberId(), connection) == 1) {
                // 계좌가 1개라면, 바로 얼마를 뺄건지 확인.
                // TODO 계좌 1개일때 송금하는거 생성.
                try {
                    connection = ConnectionProvider.getConnection();
                    List<Account> accountList = AccountDao.getInstance().selectAccountByMember(member, connection);
                    for (Account account : accountList) {
                        System.out.println(account);
                    }
                    Account account = AccountDao.getInstance().selectAccountByAccountId(account, connection);
                    System.out.println("한개인데용");
                }
            }

            // 받는사람 계좌번호의 실명 확인,.

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotAccountException e) {
            System.out.println("해당 계좌가 존재하지 않습니다.");
            System.out.println(e.getMessage());
        } finally {
            loginMenu(member);
        }
    }

}



























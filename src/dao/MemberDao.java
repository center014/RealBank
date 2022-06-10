package dao;

import model.Member;
import util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {

    private static MemberDao memberDao = new MemberDao();

    private MemberDao() {

    }

    public static MemberDao getInstance() {
        return memberDao;
    }

    // (1-1) 회원가입
    public int joinedMember(Member member, Connection connection) {
        int resultCnt = 0;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "insert into member values(member_seq.NETXTVAL,?,?,?,sysdate,sysdate)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getMemberPassword());
            resultCnt = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultCnt;
    }

    // (2-1) 로그인 시 계정 유/무 확인
    public Member existByMemberEmail(String email, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "select * from member where email=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            // TODO (resultSet.next) 이부분은 왜하는 것인지 질문.
            if (resultSet.next()) {
                Member member = new Member();
                member.setMemberId(resultSet.getLong("member_id"));
                member.setMemberName(resultSet.getString("membername"));
                member.setEmail(resultSet.getString("email"));
                member.setMemberPassword(resultSet.getString("memberpassword"));
                member.setJoinAt(resultSet.getDate("joinat"));
                member.setRecentlyLoginedAt(resultSet.getDate("recentlyloginedat"));
                return member;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // (2-2) 로그인 시 최근로그인한 시간 업데이트
    public int updateRecentlyLogin(Member member, Connection connection) {
        int resultCnt = 0;
        try {
            String sql = "update member set recentlyloginedat=sysdate where email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getEmail());
            resultCnt = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultCnt;
    }

}




















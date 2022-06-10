package model;

import java.util.Date;

public class Member {

    public Member() {

    }

    public Member(String memberName, String email, String memberPassword) {
        this.memberName = memberName;
        this.email = email;
        this.memberPassword = memberPassword;
    }

    public Member(Long memberId, String memberName, String email, String memberPassword) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.memberPassword = memberPassword;
    }

    // 시퀀스 번호
    private Long memberId;
    // 이름
    private String memberName;
    // 이메일
    private String email;
    // 비밀번호
    private String memberPassword;
    // 가입시간
    private Date joinAt;
    // 최근 로그인 시간
    private Date recentlyLoginedAt;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setRecentlyLoginedAt(Date recentlyLoginedAt) {
        this.recentlyLoginedAt = recentlyLoginedAt;
    }

    public Date getRecentlyLoginedAt() {
        return recentlyLoginedAt;
    }

    @Override
    public String toString() {
        return "Member{" +
                "[시퀀스]=" + memberId +
                ", [성명]='" + memberName + '\'' +
                ", [이메일]='" + email + '\'' +
                ", [비밀번호]='" + memberPassword + '\'' +
                ", [가입시간]=" + joinAt +
                ", [최근접속시간]=" + recentlyLoginedAt +
                '}';
    }

}

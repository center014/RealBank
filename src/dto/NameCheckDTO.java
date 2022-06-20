package dto;

public class NameCheckDTO {

    private String memberName;

    public NameCheckDTO(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return "NameCheckDTO{" +
                "[이름]='" + memberName + '\'' +
                '}';
    }
}

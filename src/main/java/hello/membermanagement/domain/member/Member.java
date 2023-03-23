package hello.membermanagement.domain.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter @Setter
public class Member {
    private Long id;
    private String name;
    private int age;
    private String birthday;
    private String phoneNumber;

    public Member() {
    }
    public Member(String name, int age, String birthday, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }
}

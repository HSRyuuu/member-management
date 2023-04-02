package hello.membermanagement.domain.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter @Setter
public class Member {
    private Long id;
    private String name;
    private Integer age;
    private String birthday;
    private String phoneNumber;
    private String email;

    public Member() {
    }
    public Member(String name, Integer age, String birthday, String phoneNumber, String email) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}

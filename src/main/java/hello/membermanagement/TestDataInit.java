package hello.membermanagement;

import hello.membermanagement.domain.admin.Admin;
import hello.membermanagement.domain.admin.AdminRepository;
import hello.membermanagement.domain.member.Member;
import hello.membermanagement.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

        private final MemberRepository memberRepository;
        private final AdminRepository adminRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        memberRepository.save(new Member("memberA", 20, "19981212", "010-2323-4545","abc123@naver.com"));
        memberRepository.save(new Member("memberB", 30, "19941125", "010-2673-4812","hello@gmail.com"));
        memberRepository.save(new Member("memberC", 50, "19890102", "010-1233-3232","mem3@gmail.com"));
    }

    @PostConstruct
    public void adminInit(){
        Admin admin = new Admin();
        admin.setId("admin1");
        admin.setLoginId("test");
        admin.setPassword("test!");
        adminRepository.save(admin);
    }
}

package hello.membermanagement.domain.login;

import hello.membermanagement.domain.admin.Admin;
import hello.membermanagement.domain.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AdminRepository adminRepository;

    public Admin login(String loginId, String password){
        return adminRepository.findByLoginId(loginId)
                .filter(a -> a.getPassword().equals(password))
                .orElse(null);
    }

}

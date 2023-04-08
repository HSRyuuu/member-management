package hello.membermanagement.domain.admin;

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

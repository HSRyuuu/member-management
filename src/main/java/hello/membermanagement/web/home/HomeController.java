package hello.membermanagement.web.home;

import hello.membermanagement.domain.admin.Admin;
import hello.membermanagement.domain.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AdminRepository adminRepository;

    //@GetMapping("/home")
    public String homeBasic(){
        return "home";
    }

    @GetMapping("/home")
    public String homeLogin(@CookieValue(name = "adminId", required = false) String adminId, Model model ){
        if(adminId == null){
            return "home";
        }

        Admin loginAdmin = adminRepository.findById(adminId);
        if(loginAdmin == null){
            return "home";
        }
        model.addAttribute("admin", loginAdmin);
        return "login/loginHome";
    }
}

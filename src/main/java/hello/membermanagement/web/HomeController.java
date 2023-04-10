package hello.membermanagement.web;

import hello.membermanagement.domain.admin.Admin;
import hello.membermanagement.domain.admin.AdminRepository;
import hello.membermanagement.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final AdminRepository adminRepository;
    private final SessionManager sessionManager;

    //@GetMapping("/home")
    public String homeBasic(){
        return "home";
    }

    //@GetMapping
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

    /** V2
     * 직접 만든 세션쿠키
     */
    @GetMapping
    public String homeLoginV2(HttpServletRequest request, Model model ){
        Admin admin = (Admin)sessionManager.getSession(request);

        if(admin == null){
            return "home";
        }
        model.addAttribute("admin", admin);
        return "login/loginHome";
    }

}

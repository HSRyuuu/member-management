package hello.membermanagement.web;

import hello.membermanagement.domain.admin.Admin;
import hello.membermanagement.domain.admin.AdminRepository;
import hello.membermanagement.web.session.SessionConst;
import hello.membermanagement.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
   // @GetMapping
    public String homeLoginV2(HttpServletRequest request, Model model ){
        Admin admin = (Admin)sessionManager.getSession(request);

        if(admin == null){
            return "home";
        }
        model.addAttribute("admin", admin);
        return "login/loginHome";
    }

    /** V3
     * HttpSession 사용
     */
    //@GetMapping
    public String homeLoginV3(HttpServletRequest request, Model model ){

        HttpSession session = request.getSession(false);
        if(session == null){
            return "home";
        }
        //세션에 "SessionConst.LOGIN_ADMIN"로 저장된 객체를 가져온다.
        Admin admin = (Admin)session.getAttribute(SessionConst.LOGIN_ADMIN);
        if(admin == null){
            return "home";
        }

        model.addAttribute("admin", admin);
        return "login/loginHome";
    }

    /** V4
     * Spring  사용
     */
    @GetMapping
    public String homeLoginV4(@SessionAttribute(name = SessionConst.LOGIN_ADMIN, required = false) Admin loginAdmin, Model model ){
        //V3에서 session을 찾고, 세션에 있는 데이터를 꺼내는 과정을 annotation으로 처리한다.
        if(loginAdmin == null){
            return "home";
        }

        model.addAttribute("admin", loginAdmin);
        return "login/loginHome";
    }

}

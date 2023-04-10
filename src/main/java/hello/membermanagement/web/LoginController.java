package hello.membermanagement.web;

import hello.membermanagement.domain.admin.Admin;
import hello.membermanagement.domain.login.LoginService;
import hello.membermanagement.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute Admin admin) {
        return "login/loginForm";
    }

    /**
     * 로그인 기능만 적용
     */
    //@PostMapping("/login")
    public String loginBasic(@Validated @ModelAttribute Admin admin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Admin loginAdmin = loginService.login(admin.getLoginId(), admin.getPassword());

        if (loginAdmin == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        return "login/loginHome";
    }

    /**
     * 로그인 쿠키 기능 추가
     */
    //@PostMapping("/login")
    public String loginV1(@Validated @ModelAttribute("admin") Admin admin, BindingResult bindingResult,
                          HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Admin loginAdmin = loginService.login(admin.getLoginId(), admin.getPassword());

        if (loginAdmin == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        model.addAttribute("admin",loginAdmin);
        //로그인 성공 시 쿠키생성
        Cookie idCookie = new Cookie("adminId", loginAdmin.getId());
        //응답에 쿠키를 담아서 보냄
        response.addCookie(idCookie);
        return "login/loginHome";
    }

    /** V2
     * 쿠키에 세션 기능 추가
     * sessionManager을 통해 쿠키를 만든다.
     */
    @PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute("admin") Admin admin, BindingResult bindingResult,
                          HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Admin loginAdmin = loginService.login(admin.getLoginId(), admin.getPassword());
        log.info("login : {}", loginAdmin);

        if (loginAdmin == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        sessionManager.createSession(loginAdmin,response);
        model.addAttribute("admin", loginAdmin);
        return "login/loginHome";
    }

    //=================================로그 아웃 =================================
   // @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        //쿠키 삭제
        expireCookie(response,"adminId");
        //index 페이지로 redirect
        return "redirect:/home";
    }
    private void expireCookie(HttpServletResponse response, String cookieName){
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /** V2
     * 세션 매니저 사용
     */
    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        //index 페이지로 redirect
        return "redirect:/home";
    }


}
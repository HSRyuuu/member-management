package hello.membermanagement.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private Map<String , Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object obj, HttpServletResponse response){

        //UUID를 만들어서 매개변수로 받은 object를 value로, UUID를 key로 sessionStore에 넣는다.
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId,obj);

        //세션 아이디로 쿠키 생성해서 response에 담아주기
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request){
        //findCookie : SESSION_COOKIE_NAME라는 이름의 쿠키가 있으면 쿠키를 반환하고, 없으면 null반환
        Cookie sessionCookie = findCookie(request,SESSION_COOKIE_NAME);
        if(sessionCookie ==null){
            return null;
        }
        //sessionCookie.getValue() : Cookie이름 반환
        //sessionStore.get() : Cookie이름으로 store에서 object를 찾아서 반환
        return sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request,SESSION_COOKIE_NAME);
        if(sessionCookie !=null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    /**
     * getSession에서 쓰는 메소드 -> private으로 지정
     */
    private Cookie findCookie(HttpServletRequest request, String cookieName){
        //httpRequest에 Cookie가 존재하지 않으면  null 반환
        if(request.getCookies() == null){
            return null;
        }
        //Cookie는 배열로 존재한다.
        Cookie myCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
        return myCookie;
    }


}

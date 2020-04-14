package web.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) {

        Collection <? extends GrantedAuthority> authorities = authentication.getAuthorities();

        authorities.forEach(authority -> {
            if(authority.getAuthority().equals("ROLE_USER")) {
                try {
                    httpServletResponse.sendRedirect("/user");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(authority.getAuthority().equals("ROLE_ADMIN")) {
                try {
                    httpServletResponse.sendRedirect("/admin");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
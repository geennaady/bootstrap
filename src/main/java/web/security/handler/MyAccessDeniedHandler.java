package web.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

            authorities.forEach(authority -> {
                if(authority.getAuthority().equals("ROLE_USER")) {
                    try {
                        httpServletResponse.sendRedirect("/user");
                    } catch (IOException ex) {
                        e.printStackTrace();
                    }
                } else if(authority.getAuthority().equals("ROLE_ADMIN")) {
                    try {
                        httpServletResponse.sendRedirect("/admin");
                    } catch (IOException ex) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}

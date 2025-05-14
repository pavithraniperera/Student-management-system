package lk.student.SMS.Security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler  {
     // This method is automatically called by Spring Security when access is denied
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, java.io.IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{ \"status\": 0, \"message\": \"Access Denied\" }");// custom json msg
    }
}

//AccessDeniedHandler is a callback interface in Spring Security used to handle 403 Forbidden errors
// — this happens after the user is authenticated,
// but doesn’t have the required roles/permissions to access a specific resource

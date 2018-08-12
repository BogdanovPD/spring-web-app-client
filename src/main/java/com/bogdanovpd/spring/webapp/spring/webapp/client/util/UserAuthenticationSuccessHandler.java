package com.bogdanovpd.spring.webapp.spring.webapp.client.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
        resp.setStatus(resp.SC_OK);

        if (containsRole(auth, "ROLE_ADMIN")) {
            resp.sendRedirect("/admin");
        }
        if (containsRole(auth, "ROLE_USER")) {
            resp.sendRedirect("/user");
        }
    }

    private boolean containsRole(Authentication auth, String role) {
        return auth.getAuthorities().stream()
                .map(au -> au.getAuthority())
                .collect(Collectors.toList()).contains(role);
    }
}

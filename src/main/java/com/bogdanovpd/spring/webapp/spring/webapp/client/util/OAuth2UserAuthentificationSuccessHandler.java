package com.bogdanovpd.spring.webapp.spring.webapp.client.util;

import com.bogdanovpd.spring.webapp.spring.webapp.client.model.Role;
import com.bogdanovpd.spring.webapp.spring.webapp.client.model.User;
import com.bogdanovpd.spring.webapp.spring.webapp.client.service.RoleRestService;
import com.bogdanovpd.spring.webapp.spring.webapp.client.service.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class OAuth2UserAuthentificationSuccessHandler implements AuthenticationSuccessHandler {

    private UserRestService userRestService;
    private  RoleRestService roleRestService;

    public OAuth2UserAuthentificationSuccessHandler(UserRestService userRestService, RoleRestService roleRestService) {
        this.userRestService = userRestService;
        this.roleRestService = roleRestService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (!(authentication instanceof  OAuth2Authentication)){
            return;
        }
        OAuth2Authentication oAuth2Auth = ((OAuth2Authentication)authentication);
        Object userDetailsObject = oAuth2Auth.getUserAuthentication().getDetails();
        Map<String, String> userDetails = (Map<String, String>) userDetailsObject;
        String userLogin = userDetails.get("email");
        User user = userRestService.getUserByLogin(userLogin);
        if (user != null) {
            httpServletResponse.setStatus(httpServletResponse.SC_OK);
            httpServletResponse.sendRedirect("/user");
            return;
        }
        user = new User();
        user.setLogin(userLogin);
        user.setPassword("OAuth2");
        user.setFirstName(userDetails.get("given_name"));
        user.setLastName(userDetails.get("family_name"));
        Role role = roleRestService.getRoleByName("ROLE_USER");
        if (role == null) {
            return;
        }
        user.setRoles(new HashSet<>(Arrays.asList(role)));
        userRestService.addUser(user);
        httpServletResponse.setStatus(httpServletResponse.SC_OK);
        httpServletResponse.sendRedirect("/user");
    }
}

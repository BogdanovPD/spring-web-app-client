package com.bogdanovpd.spring.webapp.spring.webapp.client.service;

import com.bogdanovpd.spring.webapp.spring.webapp.client.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Locale;

@Service
public class RoleFormatter implements Formatter<Role> {

    @Autowired
    private RoleRestService roleRestService;

    @Override
    public Role parse(String s, Locale locale) {
        return roleRestService.getRoleById(Long.valueOf(s));
    }

    @Override
    public String print(Role role, Locale locale) {
        return role != null ? String.valueOf(role.getId()) : "";
    }
}

package com.bogdanovpd.spring.webapp.spring.webapp.client.service;

import com.bogdanovpd.spring.webapp.spring.webapp.client.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleRestServiceImpl implements RoleRestService {

    public static final String HTTP_LOCALHOST_8080_ROLES_REST = "http://localhost:8080/roles-rest/";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Role> getAllRoles() {
        ResponseEntity<Role[]> responseEntityRole = restTemplate.getForEntity(HTTP_LOCALHOST_8080_ROLES_REST, Role[].class);
        return Arrays.asList(responseEntityRole.getBody());
    }

    @Override
    public Role getRoleById(long id) {
        return restTemplate.getForObject(HTTP_LOCALHOST_8080_ROLES_REST + id, Role.class);
    }
}

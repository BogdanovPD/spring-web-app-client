package com.bogdanovpd.spring.webapp.spring.webapp.client.service;

import com.bogdanovpd.spring.webapp.spring.webapp.client.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleRestServiceImpl implements RoleRestService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Role> getAllRoles() {
        ResponseEntity<Role[]> responseEntityRole = restTemplate.getForEntity("http://localhost:8080/roles-rest/", Role[].class);
        return Arrays.asList(responseEntityRole.getBody());
    }

    @Override
    public Role getRoleById(long id) {
        return restTemplate.getForObject("http://localhost:8080/roles-rest/" + id, Role.class);
    }
}

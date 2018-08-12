package com.bogdanovpd.spring.webapp.spring.webapp.client.service;

import com.bogdanovpd.spring.webapp.spring.webapp.client.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRestServiceImpl implements  UserRestService {

    public static final String HTTP_LOCALHOST_8080_USERS_REST = "http://localhost:8080/users-rest/";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<User> getAllUsers() {
        ResponseEntity<User[]> responseEntityUser = restTemplate.getForEntity(HTTP_LOCALHOST_8080_USERS_REST, User[].class);
        return Arrays.asList(responseEntityUser.getBody());
    }

    @Override
    public User getUserById(long id) {
        return restTemplate.getForObject(HTTP_LOCALHOST_8080_USERS_REST + id, User.class);
    }
    

    @Override
    public User getUserByLogin(String login) {
        return restTemplate.getForObject(HTTP_LOCALHOST_8080_USERS_REST + login, User.class);
    }

    @Override
    public void addUser(User user) {
        restTemplate.postForObject(HTTP_LOCALHOST_8080_USERS_REST, user, User.class);
    }

    @Override
    public void updateUser(User user) {
        restTemplate.put(HTTP_LOCALHOST_8080_USERS_REST + user.getId(), user);
    }

    @Override
    public void deleteUser(long id) {
        restTemplate.delete(HTTP_LOCALHOST_8080_USERS_REST + id);
    }
}

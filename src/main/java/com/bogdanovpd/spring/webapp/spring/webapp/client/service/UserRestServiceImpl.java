package com.bogdanovpd.spring.webapp.spring.webapp.client.service;

import com.bogdanovpd.spring.webapp.spring.webapp.client.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRestServiceImpl implements  UserRestService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<User> getAllUsers() {
        ResponseEntity<User[]> responseEntityUser = restTemplate.getForEntity("http://localhost:8080/users-rest/", User[].class);
        return Arrays.asList(responseEntityUser.getBody());
    }

    @Override
    public User getUserById(long id) {
        return restTemplate.getForObject("http://localhost:8080/users-rest/" + id, User.class);
    }

    @Override
    public User getUserByLogin(String login) {
        return restTemplate.getForObject("http://localhost:8080/users-rest/" + login, User.class);
    }

    @Override
    public void addUser(User user) {
        restTemplate.postForObject("http://localhost:8080/users-rest/", user, User.class);
    }

    @Override
    public void updateUser(User user) {
        restTemplate.put("http://localhost:8080/users-rest/" + user.getId(), user);
    }

    @Override
    public void deleteUser(long id) {
        restTemplate.delete("http://http://localhost:8080/users-rest/" + id);
    }
}

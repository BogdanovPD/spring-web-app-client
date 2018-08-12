package com.bogdanovpd.spring.webapp.spring.webapp.client.service;

import com.bogdanovpd.spring.webapp.spring.webapp.client.model.User;

import java.util.List;

public interface UserRestService {

    List<User> getAllUsers();
    User getUserById(long id);
    User getUserByLogin(String login);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(long id);

}

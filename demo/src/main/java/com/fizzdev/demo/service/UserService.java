package com.fizzdev.demo.service;

import com.fizzdev.demo.dao.domain.User;

import java.util.List;

public interface UserService {

    boolean queueUser(String userName, String firstName, String lastName, String birth);

    void saveUser(User user);

    List<User> fetchAllUsers();
}

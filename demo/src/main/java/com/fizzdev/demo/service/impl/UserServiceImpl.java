package com.fizzdev.demo.service.impl;

import com.fizzdev.demo.dao.domain.User;
import com.fizzdev.demo.dao.repository.UserRepository;
import com.fizzdev.demo.message.RabbitmqPublisher;
import com.fizzdev.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RabbitmqPublisher rabbitmqPublisher;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean queueUser(String userName, String firstName, String lastName, String birth) {

        if (userName == null || userName.isEmpty()) {
            return false;
        }

        if (userRepository.existsUserByUserName(userName)) {
            return false;
        }

        User user = new User(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (!birth.isEmpty()) {
            try {
                user.setBirth(java.sql.Date.valueOf(LocalDate.parse(birth)));
            } catch (DateTimeParseException e) {
                return false;
            }
        }

        rabbitmqPublisher.produceMsg(user);
        return true;
    }

    @Override
    public void saveUser(User user) {
        if (user == null) return;

        userRepository.save(user);
    }

    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }
}

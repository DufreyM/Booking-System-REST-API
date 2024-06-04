package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(String id);
    List<User> getAllUsers();
    User updateUser(String id, User user);
    void deleteUser(String id);
}

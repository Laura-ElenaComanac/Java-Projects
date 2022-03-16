package com.license.ProjectSocialNetwork.services;

import com.license.ProjectSocialNetwork.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    void saveUser(User user);
    void deleteUser(Long id);
    void updateUser(User user);
}

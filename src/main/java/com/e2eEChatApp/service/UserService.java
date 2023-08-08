package com.e2eEChatApp.service;

import com.e2eEChatApp.exception.UserException;
import com.e2eEChatApp.model.User;
import com.e2eEChatApp.request.UpdateUserRequest;

import java.util.List;
public interface UserService {

    public User findUserById(Integer id) throws UserException;
    public User findUserProfile(String jwt) throws UserException;
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
    public List<User> searchUser(String query);
}

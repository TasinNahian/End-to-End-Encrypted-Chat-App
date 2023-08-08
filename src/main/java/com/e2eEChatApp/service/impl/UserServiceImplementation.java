package com.e2eEChatApp.service.impl;

import com.e2eEChatApp.config.TokenProvider;
import com.e2eEChatApp.exception.UserException;
import com.e2eEChatApp.model.User;
import com.e2eEChatApp.repository.UserRepository;
import com.e2eEChatApp.request.UpdateUserRequest;
import com.e2eEChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    @Override
    public User findUserById(Integer id) throws UserException{
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }throw new UserException("User not found with id "+id);
    }
    @Override
    public User findUserProfile(String jwt) throws UserException {
        //this method is used for login
        String email = tokenProvider.getEmailFromToken(jwt);
        if(email.isEmpty()){
            throw new BadCredentialsException("Received invalid token");
        }
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserException("User not found with email "+ email);
        }
        return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        User user = findUserById(userId);
        if(req.getFullName() !=null){
            user.setFullName(req.getFullName());
        }
        if(req.getProfilePicture()!=null){
            user.setProfilePicture(req.getProfilePicture());
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
//        List<User> users = userRepository.searchUser(query);
//        return users;
        return null;
    }
}

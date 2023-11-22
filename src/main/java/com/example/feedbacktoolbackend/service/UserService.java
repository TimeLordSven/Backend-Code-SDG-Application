package com.example.feedbacktoolbackend.service;

import com.example.feedbacktoolbackend.data.Models.User;
import com.example.feedbacktoolbackend.data.UserRepository;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public UserBusiness getUser(Long id){
        return convertToBusinessModel(userRepository.findById(id));
    }
    private UserBusiness convertToBusinessModel(Optional<User> userData){
        return userData.map(user -> new UserBusiness(user.id,user.firstName,user.lastName)).orElse(null);
    }
}

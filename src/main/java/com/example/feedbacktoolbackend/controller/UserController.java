package com.example.feedbacktoolbackend.controller;

import com.example.feedbacktoolbackend.controller.dto.UserDTO;
import com.example.feedbacktoolbackend.service.UserService;
import com.example.feedbacktoolbackend.service.models.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
@GetMapping
    public UserDTO getUser(){
        UserBusiness user = userService.getUser(1L);
        return convertToDTO(user);
    }
    private UserDTO convertToDTO(UserBusiness userBusiness){
        return new UserDTO(userBusiness.getId(),userBusiness.getFirstName(), userBusiness.getLastName());
    };
}

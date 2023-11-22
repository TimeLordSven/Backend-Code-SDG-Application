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

    /**
     * Constructor injection for UserController.
     * @author Sven Molenaar
     * @param userService The service for user-related operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles GET requests to retrieve user data and converts it to a UserDTO.
     * @author Sven Molenaar
     * @return UserDTO containing user information fetched from the service.
     */
    @GetMapping
    public UserDTO getUser(){
        UserBusiness user = userService.getUser(1L);
        return convertToDTO(user);
    }

    /**
     * Converts UserBusiness object to UserDTO object.
     * @author Sven Molenaar
     * @param userBusiness The UserBusiness object to be converted.
     * @return UserDTO containing user information.
     */
    private UserDTO convertToDTO(UserBusiness userBusiness){
        return new UserDTO(userBusiness.getId(), userBusiness.getFirstName(), userBusiness.getLastName());
    }
}

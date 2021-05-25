package com.javamentor.springcrudsecuritybootfrom1.controllers;

import com.javamentor.springcrudsecuritybootfrom1.Model.User;
import com.javamentor.springcrudsecuritybootfrom1.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/users/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserControllersRest {

    private UserServiceImpl userService;

    @Autowired
    public UserControllersRest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/principal")
    public ResponseEntity<User> principalUser(Principal principal) {

        return ResponseEntity.ok(userService.getUserByUsername(principal.getName()));
    }




}

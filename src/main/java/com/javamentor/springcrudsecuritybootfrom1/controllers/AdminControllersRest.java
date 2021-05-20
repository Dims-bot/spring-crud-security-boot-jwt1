package com.javamentor.springcrudsecuritybootfrom1.controllers;

import com.javamentor.springcrudsecuritybootfrom1.Model.User;
import com.javamentor.springcrudsecuritybootfrom1.service.UserServiceImpl;
import com.javamentor.springcrudsecuritybootfrom1.transferObject.NewUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/users/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminControllersRest {

    private UserServiceImpl userService;

    @Autowired
    public AdminControllersRest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> userList = userService.getAllUsers();

        return userList != null && !userList.isEmpty()
                ? ResponseEntity.ok(userList)
                //new ResponseEntity<>(userList, HttpStatus.OK)
                : ResponseEntity.notFound().build();
                //new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        //User user = userService.getUserById(id);
        //return new ResponseEntity<>(user, HttpStatus.OK);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/principal")
    public ResponseEntity<User> principal(Principal principal) {
        //User user = userService.getUserByUsername(principal.getName());

        return ResponseEntity.ok(userService.getUserByUsername(principal.getName()));
    }


    @PostMapping(value = "/newUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> newUserCreate(@RequestBody NewUserRequest newUserRequest) {
        if (userService.existsByUsername(newUserRequest.getUsername())) {

            //return new ResponseEntity<>("Username is exist", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body("Username is exist");
        }

        userService.save(newUserRequest);
        //return new ResponseEntity<>(HttpStatus.CREATED);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @PatchMapping(value = "/{id}/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @PathVariable String username, @RequestBody NewUserRequest userRequest) {
        if (userService.existsByUsername(userRequest.getUsername()) & !(userRequest.getUsername().equals(username))
               // & !(userRequest.getUsername()).equals((userService.getUserById(id)).getUsername())
        )
        {

            // return new ResponseEntity<>("Username is exist" ,HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body("Username is exist");
        }
        userService.updateUser(id, userRequest);
        //return new ResponseEntity<>(HttpStatus.OK);
        return ResponseEntity.ok().build();
    }

}

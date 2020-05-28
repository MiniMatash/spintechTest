package com.minimatash.spintech.controller;

import com.minimatash.spintech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity registerUser(@RequestParam String email,
                                               @RequestParam String password) {
        if (userService.registerUser(email, password) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}

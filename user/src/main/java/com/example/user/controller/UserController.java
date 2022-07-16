package com.example.user.controller;

import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JLS
 * @description:
 * @since 2022-07-16 17:05
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("test")
    public String test(){
        return userService.test();
    }
}

package com.jiang.controller;

import com.jiang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('Read Permission')")
    @GetMapping("/hello")
    public String test(){
        return "hello";
    }

}

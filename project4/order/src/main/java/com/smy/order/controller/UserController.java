package com.smy.order.controller;

import com.smy.order.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @RequestMapping("/query1")
    public String query1() {
        return userService.getUser();
    }
    
    @RequestMapping("/query2")
    public String query2() {
        return userService.getUser();
    }
}

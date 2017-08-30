package com.moredo.example.oauth.server.controller;

import com.moredo.example.oauth.server.entity.User;
import com.moredo.example.oauth.server.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OAuth2Controller {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @RequestMapping("/user")
    @ResponseBody
    public Principal getUser(Principal user) {
        return user;
    }

    @RequestMapping("userinfo")
    public User getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetailsService.loadUserByUserId(userDetails.getUsername());
    }

}

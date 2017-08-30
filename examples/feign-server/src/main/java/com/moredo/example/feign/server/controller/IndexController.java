package com.moredo.example.feign.server.controller;

import com.moredo.example.feign.server.entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<User> list() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(i + 1);
            user.setName("George" + i);
            users.add(user);
        }
        return users;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public User add(User user) {
        return user;
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public String delete(String name) {
        return "delete user : " + name;
    }

    @RequestMapping(value = "file", method = RequestMethod.POST)
    public String file(@RequestParam(value = "file") MultipartFile file) {
        return "file size : " + file.getSize();
    }

    @RequestMapping(value = "header", method = RequestMethod.POST)
    public String header(@RequestHeader(value = "access_token") String token) {
        return "header : " + token;
    }

}

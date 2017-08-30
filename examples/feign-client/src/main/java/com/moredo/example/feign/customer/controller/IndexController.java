package com.moredo.example.feign.customer.controller;

import com.moredo.example.feign.customer.client.FeignServerClient;
import com.moredo.example.feign.customer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private FeignServerClient feignServerClient;

    @RequestMapping(value = "list")
    public List<User> feign() {
        try {
            return feignServerClient.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "add")
    public User add() {
        try {
            return feignServerClient.add("George", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "delete")
    public String delete() {
        try {
            return feignServerClient.delete("George");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "file", method = RequestMethod.POST)
    public String file(@RequestParam(value = "file") MultipartFile file) {
        return feignServerClient.file(file);
    }

}

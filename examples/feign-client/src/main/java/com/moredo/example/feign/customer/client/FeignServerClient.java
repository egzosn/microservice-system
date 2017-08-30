package com.moredo.example.feign.customer.client;

import com.moredo.example.feign.customer.client.callback.FeignServerCallback;
import com.moredo.example.feign.customer.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "feign-server", fallback = FeignServerCallback.class)
public interface FeignServerClient {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    List<User> list();

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    User add(@RequestParam(value = "name") String name, @RequestParam(value = "age") int age);

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    String delete(@RequestParam(value = "name") String name);

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    String file(@RequestPart(value = "file") MultipartFile file);

    @RequestMapping(value = "/header", method = RequestMethod.POST)
    String header(@RequestHeader(value = "access_token") String token);

}

package com.moredo.example.feign.customer.client.callback;

import com.moredo.example.feign.customer.client.FeignServerClient;
import com.moredo.example.feign.customer.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeignServerCallback implements FeignServerClient {

    @Override
    public List<User> list() {
        return new ArrayList<>();
    }

    @Override
    public User add(@RequestParam(value = "name") String name, @RequestParam(value = "age") int age) {
        User user = new User();
        user.setAge(-1);
        user.setName("user add failed");
        return user;
    }

    @Override
    public String delete(@RequestParam(value = "name") String name) {
        return "user delete failed";
    }

    @Override
    public String file(@RequestPart(value = "file") MultipartFile file) {
        return "file upload failed";
    }

    @Override
    public String header(String token) {
        return "no header";
    }
}

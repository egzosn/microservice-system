package com.moredo.example.mybatis.controller;

import com.moredo.common.web.controller.BaseController;
import com.moredo.example.mybatis.entity.User;
import com.moredo.example.mybatis.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "用户管理")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "获取所有用户")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public Map<String, Object> getUsers() {
        List<User> users = userMapper.getAll();
        return success("data", users);
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Map<String, Object> getUser(Long id) {
        User user = userMapper.getOne(id);
        return success("data", user);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> save(User user) {
        userMapper.insert(user);
        return success();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Map<String, Object> update(User user) {
        userMapper.update(user);
        return success();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@PathVariable("id") Long id) {
        userMapper.delete(id);
        return success();
    }


}
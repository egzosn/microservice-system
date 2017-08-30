package com.moredo.example.oauth.resource.controller;

import com.moredo.common.oauth.OAuthUtil;
import com.moredo.common.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(description = "测试")
@RestController
@RequestMapping(value = "/test")
public class TestController extends BaseController {

    @ApiOperation(value = "oauth权限验证")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @RequestMapping(value = "oauth", method = RequestMethod.POST)
    public Map<String, Object> oauth() {
        return success("userId", OAuthUtil.getUserId());
    }

    @RequestMapping(value = "anyone", method = RequestMethod.GET)
    public Map<String, Object> anyone() {
        return success();
    }

}
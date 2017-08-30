package com.moredo.example.oauth.client.controller;

import com.moredo.common.utils.http.RestTemplateUtil;
import com.moredo.common.web.controller.BaseController;
import com.moredo.example.oauth.client.mode.AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 登录控制类
 *
 * @author 肖红星
 * @create 2017/3/9
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    //权限系统
    @Value("${moredo.auth.server}")
    private String server;
    //客户端ID
    @Value("${moredo.auth.client_id}")
    private String client_id;
    //客户端密钥
    @Value("${moredo.auth.client_secret}")
    private String client_secret;
    //授权类型
    @Value("${moredo.auth.grant_type}")
    private String grant_type;
    //授权范围
    @Value("${moredo.auth.scope}")
    private String scope;

    public static AccessToken accessToken = null;


    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(String username, String password) {
        try {
            //请求参数
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("client_id", client_id);
            params.add("client_secret", client_secret);
            params.add("grant_type", grant_type);
            params.add("scope", scope);
            params.add("username", username);
            params.add("password", password);
            accessToken = restTemplateUtil.post(server, AccessToken.class, params);
            if (accessToken == null || StringUtils.isEmpty(accessToken.getAccess_token())) {
                return failed(500);
            }
            return success();
        } catch (Exception e) {
            logger.error("登录异常：", e);
            return failed(500);
        }
    }

}

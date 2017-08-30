package com.moredo.example.oauth.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.moredo.common.utils.http.RestTemplateUtil;
import com.moredo.common.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

/**
 * 测试类
 *
 * @author admin
 * @create 2017/3/15
 */
@RestController
@RequestMapping(value = "/test")
public class TestController extends BaseController {

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @RequestMapping(value = "has", method = RequestMethod.POST)
    public Map<String, Object> has() {
        try {
//            //获取用户信息
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//            params.add("access_token", LoginController.accessToken.getAccess_token());
//            Map<String, String> headerMap = new HashMap<>();
//            headerMap.put("access_token", LoginController.accessToken.getAccess_token());
//            JSONObject jsonObject = restTemplateUtil.post("http://127.0.0.1:9999/userinfo", JSONObject.class, params, null, headerMap);
//            System.out.println(jsonObject);

            //请求参数
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("access_token", LoginController.accessToken.getAccess_token());
            JSONObject jsonObject = restTemplateUtil.post("http://127.0.0.1:8087/test/oauth", JSONObject.class, params, null, null);
            return success("data", jsonObject);
        } catch (HttpClientErrorException hee){
            if(hee.getStatusCode().value() == 401 ){
                return failed(99);
            }else if(hee.getStatusCode().value() == 403){
                return failed(77);
            }
            return failed(500);
        }catch (Exception e) {
            return failed(500);
        }
    }

}

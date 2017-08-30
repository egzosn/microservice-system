package com.moredo.example.oauth.resource;

import com.moredo.common.oauth.ResourceServerConfig;
import com.moredo.common.web.config.Swagger2;
import com.moredo.common.web.config.error.ErrorCodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@Import({Swagger2.class, ErrorCodeProperties.class, MethodValidationPostProcessor.class, ResourceServerConfig.class})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}

package com.moredo.example.oauth.client;

import com.moredo.common.utils.http.RestTemplateUtil;
import com.moredo.common.web.config.RestTemplateConfig;
import com.moredo.common.web.config.error.ErrorCodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SprintBoot启动类
 *
 * @author 肖红星
 * @create 2016/10/10
 */
@SpringBootApplication
@EnableFeignClients
@Import({RestTemplateConfig.class, ErrorCodeProperties.class})
public class OAuthClientApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(OAuthClientApplication.class, args);
    }

    @Bean
    public RestTemplateUtil restTemplateUtil() {
        return new RestTemplateUtil();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/index.html").setViewName("index");
    }

}

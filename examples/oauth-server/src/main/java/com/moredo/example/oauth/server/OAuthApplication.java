package com.moredo.example.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@EnableResourceServer
@SessionAttributes(value = "authorizationRequest")
public class OAuthApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }

//    @Bean
//    public TokenStore tokenStore() {
////        return new RedisTokenStore(redisConnectionFactory);
////        return new JdbcTokenStore(dataSource);
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "huodull.com".toCharArray());
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
//        return converter;
//    }


}

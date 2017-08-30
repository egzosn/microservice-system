package com.moredo.example.oauth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * 配置用户信息，以及受保护路径、允许访问路径
 */
@Configuration
@EnableWebSecurity
@Order(-20)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public DataSource dataSource;

    @Autowired
    private MD5AuthenticationProvider md5AuthenticationProvider;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder passwordEncoder = new MD5PasswordEncoder();
//        return passwordEncoder;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置不拦截路径
        http.authorizeRequests().antMatchers("/info", "/health", "metrics").permitAll().anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .requestMatchers()
                .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/info", "/health")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(md5AuthenticationProvider);
    }

}

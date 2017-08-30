package com.moredo.example.mybatis;

import com.moredo.common.web.config.Swagger2;
import com.moredo.common.web.config.error.ErrorCodeProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.moredo.example.mybatis.mapper")
@Import({Swagger2.class, ErrorCodeProperties.class})
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}

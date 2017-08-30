package com.moredo.examples.stream2;

import com.moredo.examples.stream2.processor.Barista;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@EnableBinding(Barista.class)
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}

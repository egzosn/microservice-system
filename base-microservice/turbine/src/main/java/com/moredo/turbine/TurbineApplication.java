package com.moredo.turbine;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
@Controller
public class TurbineApplication {

    @RequestMapping(value = "/")
    public String index() {
        return "forward:/hystrix";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(TurbineApplication.class).web(true).run(args);
    }
}
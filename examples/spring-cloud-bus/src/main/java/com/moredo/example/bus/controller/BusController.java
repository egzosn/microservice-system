package com.moredo.example.bus.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
@RefreshScope
public class BusController {

    @Value("${service.prop:default}")
    private String serviceProp;

    @RequestMapping(value = "show")
    public String show(){
        return serviceProp;
    }

}

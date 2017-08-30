package com.moredo.examples.stream1.web;

import com.moredo.examples.stream1.rabbit.RabbitSender;
import com.moredo.examples.stream1.rabbit.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    private Send send;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        if(send != null){
            send.sendMsg("aaaaaaaaaaa");
        }
        rabbitSender.sendMessage("I'm Stream Client 1");
        return "消息发送成功！";
    }

}

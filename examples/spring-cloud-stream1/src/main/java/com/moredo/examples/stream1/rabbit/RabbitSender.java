package com.moredo.examples.stream1.rabbit;

import com.moredo.examples.stream1.processor.Barista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RabbitSender {

    @Autowired
    private Barista source;

    // 发送消息
    public void sendMessage(Object object) {
        Message message = MessageBuilder.withPayload(object).setReplyChannel(source.errorOutput()).setErrorChannel(source.errorOutput()).build();
        source.output().send(message, 3000);
        System.out.println("消息发送");
    }

    @StreamListener(Barista.REPLY_INPUT)
    public void replyIn(Message<Object> message) {
        Object object = message.getPayload();
        System.out.println("收到回执消息：" + object);
    }

    @StreamListener(Barista.ERROR_INPUT)
    public void errorInput(Message<Object> message) {
        System.out.println("收到错误消息!");
    }

}  
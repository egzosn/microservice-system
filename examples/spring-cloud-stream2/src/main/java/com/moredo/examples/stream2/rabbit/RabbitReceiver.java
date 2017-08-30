package com.moredo.examples.stream2.rabbit;

import com.moredo.examples.stream2.processor.Barista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class RabbitReceiver {

    @Autowired
    private Barista source;

    @StreamListener(Barista.INPUT)
    @SendTo(Barista.REPLY_OUTPUT)
    public Message receiver(Message<Object> message) {
        Object object = message.getPayload();
        System.out.println("Client2收到条新消息:" + object);
        //消息回执
        return MessageBuilder.withPayload("你的消息已被Client 2接收").build();
    }
}
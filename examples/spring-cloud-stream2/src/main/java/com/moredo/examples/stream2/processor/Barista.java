package com.moredo.examples.stream2.processor;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Barista extends Processor{

    //回执消息接收通道
    String REPLY_INPUT = "reply_input";
    //回执消息发送通道
    String REPLY_OUTPUT = "reply_output";

    @Input(REPLY_INPUT)
    SubscribableChannel replyInput();

    @Output(REPLY_OUTPUT)
    MessageChannel replyOutput();

    String ERROR_INPUT = "error_input";
    String ERROR_OUTPUT = "error_output";

    @Input(ERROR_INPUT)
    SubscribableChannel errorInput();

    @Input(ERROR_OUTPUT)
    MessageChannel errorOutput();
      
}  
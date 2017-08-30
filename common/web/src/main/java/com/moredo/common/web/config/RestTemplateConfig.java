package com.moredo.common.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate配置
 *
 * @author 肖红星
 * @create 2016/11/11
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(15 * 1000);
        factory.setConnectTimeout(15 * 1000);
        return new RestTemplate(factory);
    }

//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
//        RestTemplate template = new RestTemplate(factory);
//        return template;
//    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //数据读取超时时间，单位为毫秒
        factory.setReadTimeout(1000 * 60);
        //连接超时时间，单位为毫秒ZX
        factory.setConnectTimeout(1000 * 60);
        return factory;
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        HttpComponentsAsyncClientHttpRequestFactory factory = new HttpComponentsAsyncClientHttpRequestFactory();
        factory.setReadTimeout(15 * 1000);
        factory.setConnectTimeout(15 * 1000);
        return new AsyncRestTemplate(factory);
    }
}
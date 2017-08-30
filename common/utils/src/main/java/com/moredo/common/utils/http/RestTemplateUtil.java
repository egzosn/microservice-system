package com.moredo.common.utils.http;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 *
 * @author 肖红星
 * @create 2016/11/11
 */
@Component
public class RestTemplateUtil {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    /**
     * 发送get请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test?keyStr={keyStr}&sign={sign}&account=test_account&password=123456
     * @param responseType 返回结果类型，例如：String.class
     * @param urlVariables url对应的参数，例如：keyStr, sign
     * @return 返回T
     */
    public <T> T get(String url, Class<T> responseType, Object... urlVariables) {
        return this.get(url, responseType, null, urlVariables);
    }

    /**
     * 发送get请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test?keyStr={keyStr}&sign={sign}&account=test_account&password=123456
     * @param responseType 返回结果类型，例如：String.class
     * @param headerMap    请求头参数
     * @param urlVariables url对应的参数，例如：keyStr, sign
     * @return 返回T
     */
    public <T> T get(String url, Class<T> responseType, Map<String, String> headerMap, Object... urlVariables) {
        return this.get(url, responseType, null, headerMap, urlVariables);
    }

    /**
     * 发送get请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test?keyStr={keyStr}&sign={sign}&account=test_account&password=123456
     * @param responseType 返回结果类型，例如：String.class
     * @param mediaType    请求类型
     * @param headerMap    请求头参数
     * @param urlVariables url对应的参数，例如：keyStr, sign
     * @return 返回T
     */
    public <T> T get(String url, Class<T> responseType, MediaType mediaType, Map<String, String> headerMap, Object... urlVariables) {
        return restTemplate.exchange(url, HttpMethod.GET, buildHeader(mediaType, headerMap, null), responseType, urlVariables).getBody();
    }

    /**
     * 发送post请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test?keyStr={keyStr}&sign={sign}&account=test_account&password=123456
     * @param responseType 返回结果类型，例如：String.class
     * @param params       Map请求参数
     * @return
     */
    public <T> T post(String url, Class<T> responseType, Object params) {
        return post(url, responseType, params, null);
    }

    /**
     * 发送post请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test?keyStr={keyStr}&sign={sign}&account=test_account&password=123456
     * @param responseType 返回结果类型，例如：String.class
     * @param params       Map请求参数
     * @param mediaType    请求内容类型
     * @return
     */
    public <T> T post(String url, Class<T> responseType, Object params, MediaType mediaType) {
        return this.post(url, responseType, params, mediaType, null);
    }

    /**
     * 发送post请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test?keyStr={keyStr}&sign={sign}&account=test_account&password=123456
     * @param responseType 返回结果类型，例如：String.class
     * @param params       Map请求参数
     * @param mediaType    请求内容类型
     * @return
     */
    public <T> T post(String url, Class<T> responseType, Object params, MediaType mediaType, Map<String, String> headerMap) {
        return restTemplate.postForObject(url, buildHeader(mediaType, headerMap, params), responseType);
    }

    /**
     * 发送异步get请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test?keyStr={keyStr}&sign={sign}&account=test_account&password=123456
     * @param responseType 返回结果类型，例如：String.class
     * @param urlVariables url对应的参数，例如：keyStr, sign
     * @return 返回T
     */
    public <T> void asyncGet(String url, Class<T> responseType, SuccessCallback successCallback, FailureCallback failureCallback, Object... urlVariables) {
        asyncGet(url, responseType, null, null, successCallback, failureCallback, urlVariables);
    }

    /**
     * 发送异步get请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test?keyStr={keyStr}&sign={sign}&account=test_account&password=123456
     * @param responseType 返回结果类型，例如：String.class
     * @param mediaType    请求内容类型
     * @param headerMap    请求头参数
     * @param urlVariables url对应的参数，例如：keyStr, sign
     * @return 返回T
     */
    public <T> void asyncGet(String url, Class<T> responseType, MediaType mediaType, Map<String, String> headerMap, SuccessCallback successCallback, FailureCallback failureCallback, Object... urlVariables) {
        ListenableFuture<ResponseEntity<T>> future = asyncRestTemplate.exchange(url, HttpMethod.GET, buildHeader(mediaType, headerMap, null), responseType, urlVariables);
        future.addCallback(successCallback, failureCallback);
    }

    /**
     * 发送异步post请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test
     * @param responseType 返回结果类型，例如：String.class
     * @param params       Map请求参数
     * @return
     */
    public <T> void asyncPost(String url, Class<T> responseType, Object params, SuccessCallback successCallback, FailureCallback failureCallback) {
        asyncPost(url, responseType, null, params, successCallback, failureCallback);
    }

    /**
     * 发送异步post请求
     *
     * @param url          请求地址，例如：http://127.0.0.1:9095/sync/test
     * @param responseType 返回结果类型，例如：String.class
     * @param params       Map请求参数
     * @param mediaType    请求内容类型
     * @return
     */
    public <T> void asyncPost(String url, Class<T> responseType, MediaType mediaType, Object params, SuccessCallback successCallback, FailureCallback failureCallback) {
        this.asyncPost(url, responseType, mediaType, params, null, successCallback, failureCallback);
    }

    public <T> void asyncPost(String url, Class<T> responseType, MediaType mediaType, Object params, Map<String, String> headerMap, SuccessCallback successCallback, FailureCallback failureCallback) {
        HttpHeaders headers = null;
        if (mediaType != null) {
            headers = new HttpHeaders();
            headers.setContentType(mediaType);
        }
        ListenableFuture<ResponseEntity<T>> future = asyncRestTemplate.postForEntity(url, buildHeader(mediaType, headerMap, params), responseType);
        future.addCallback(successCallback, failureCallback);
    }

    /**
     * 生成请求头信息
     *
     * @param mediaType 请求内容类型
     * @param headerMap 请求头参数
     * @param params    请求参数
     * @return
     */
    public HttpEntity<Object> buildHeader(MediaType mediaType, Map<String, String> headerMap, Object params) {
        if(mediaType == null){
            mediaType = MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        }
        HttpHeaders headers = null;
        if (mediaType != null || headerMap != null) {
            headers = new HttpHeaders();
            //设置请求类型类型
            if (mediaType != null) {
                headers.setContentType(mediaType);
            }
            if (headerMap != null && !headerMap.isEmpty()) {
                headers.setAll(headerMap);
            }
        }
        return new HttpEntity<Object>(params, headers);
    }





    /**
     *初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
     * 添加的StringHttpMessageConverter非UTF-8
     * 所以先要移除原有的StringHttpMessageConverter，
     * 再添加一个字符集{@param charset }的StringHttpMessageConvert
     * @param charset
     */
    public RestTemplate setMessageConverterCharset(String charset) {

        return setMessageConverterCharset(restTemplate, charset);
    }


    /**
     *初始化RestTemplate，RestTemplate会默认添加HttpMessageConverter
     * 添加的StringHttpMessageConverter非UTF-8
     * 所以先要移除原有的StringHttpMessageConverter，
     * 再添加一个字符集{@param charset }的StringHttpMessageConvert
     * @param charset
     */
    public RestTemplate setMessageConverterCharset( RestTemplate restTemplate, String charset) {
        if (StringUtils.isEmpty(charset)){
            return restTemplate;
        }
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }

        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(Charset.forName(charset));
        converterList.add(converter);
        return restTemplate;
    }

}

var server = "";

/**
 * 发送ajax POST请求
 * @param url 请求地址
 * @param async 是否异步
 * @param params 请求参数
 * @param dataType 返回数据类型
 * @param beforeSend 请求发送前事件
 * @param successHandler 请求成功处理事件
 * @param errorHandler 请求失败处理事件
 * @param completeHandler 请求完成处理事件
 */
function post(url, async, params, dataType, successHandler, errorHandler, beforeSend, completeHandler) {
    $.ajax({
        url: server + url,
        type: "POST",
        async: async,    //true异步，false同步
        data: params, //请求参数
        timeout: 5000,    //超时时间
        dataType: dataType,    //返回的数据格式：json/xml/html/script/jsonp/text
        beforeSend: beforeSend, //请求发送前需要执行的事件
        success: successHandler, //请求成功处理事件：function (data, textStatus, jqXHR) { console.log(data);console.log(textStatus);console.log(jqXHR);}
        error: errorHandler, //请求失败处理事件：function (xhr, textStatus) {console.log('错误');console.log(xhr);console.log(textStatus)}
        complete: completeHandler //请求结束处理事件
    })
}

/**
 * 发送ajax GET请求
 * @param url 请求地址
 * @param async 是否异步
 * @param params 请求参数
 * @param dataType 返回数据类型
 * @param beforeSend 请求发送前事件
 * @param successHandler 请求成功处理事件
 * @param errorHandler 请求失败处理事件
 * @param completeHandler 请求完成处理事件
 */
function get(url, async, params, dataType, successHandler, errorHandler, completeHandler, beforeSend) {
    $.ajax({
        url: server + url,
        type: "GET",
        async: async,    //或false,是否异步
        data: params, //请求参数
        timeout: 5000,    //超时时间
        dataType: dataType,    //返回的数据格式：json/xml/html/script/jsonp/text
        beforeSend: beforeSend, //请求发送前需要执行的事件
        success: successHandler, //请求成功处理事件：function (data, textStatus, jqXHR) { console.log(data);console.log(textStatus);console.log(jqXHR);}
        error: errorHandler, //请求失败处理事件：function (xhr, textStatus) {console.log('错误');console.log(xhr);console.log(textStatus)}
        complete: completeHandler //请求结束处理事件
    })
}

/**
 * 默认异常处理
 * @param message
 * @param defaultMessage
 */
function defaultErrorHandler(message, defaultMessage) {
    if(defaultMessage == null || defaultMessage.length == 0){
        defaultMessage = "服务器异常！";
    }
    alert(message != null && message.length > 0 ? message : defaultMessage);
}
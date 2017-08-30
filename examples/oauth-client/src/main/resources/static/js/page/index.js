function testOpen() {
    var params = {};
    //发送POST请求
    // post("/login", true, params, "json", success, fail, null, null);
}

function testDisabled() {
    var params = {};
    //发送POST请求
    // post("/login", true, params, "json", success, fail, null, null);
}

function testHasAuth() {
    var params = {};
    //发送POST请求
    post("/test/has", true, params, "json", success, fail, null, null);
}

/**
 * 请求成功
 * @param data
 * @param status
 */
function success(data, textStatus, jqXHR) {
    alert(JSON.stringify(data));
}

/**
 * 请求失败
 * @param xhr
 * @param textStatus
 */
function fail(xhr, textStatus) {
    alert("请求失败：" + JSON.stringify(xhr));
}
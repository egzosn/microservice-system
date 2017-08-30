/**
 * 发送登录请求
 */
function login() {
    //获取用户名
    var username = $("#username").val();
    if(username.length == 0){
        $("#username").focus();
        alert("登录帐号不允许为空!")
        return ;
    }
    //获取密码
    var password = $("#password").val();
    if(password.length == 0){
        $("#password").focus();
        alert("登录密码不允许为空!")
        return ;
    }
    var params = {};
    //设置用户名
    params.username = username;
    //设置密码
    params.password = password;
    //发送POST请求
    post("/login", true, params, "json", loginSuccess, loginFail, null, null);
};

/**
 * 登录成功
 * @param data
 * @param status
 */
function loginSuccess(data, textStatus, jqXHR) {
    if (data.code != 0) {
        //登录失败，刷新校验码
        if(data.message){
            alert(data.message);
        }else{
            alert("登录失败!");
        }
    } else {
        //登录成功，进入后台首页
        self.location.href = "/index.html";
    }
}

/**
 * 登录失败
 * @param xhr
 * @param textStatus
 */
function loginFail(xhr, textStatus) {
    alert("登录失败!");
}
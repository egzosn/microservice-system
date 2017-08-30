#授权模式
http://127.0.0.1:9999/oauth/authorize?client_id=unity-client&redirect_uri=[回调地址]&response_type=code&scope=read write&state=[UUID]

#简化授权模式
http://127.0.0.1:9999/oauth/authorize?client_id=acme&redirect_uri=[回调地址]&response_type=token&scope=read write&state=[UUID]

#密码模式
http://127.0.0.1:9999/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read write&username=admin&password=123456

#刷新令牌
http://127.0.0.1:9999/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=[授权通过后返回会refresh_token]

#生成jwt.jks
keytool -genkeypair -alias jwt -keyalg RSA -dname "CN=jwt, L=BH, S=BH, C=BR" -keypass lbd.com -keystore jwt.jks -storepass lbd.com
#生成public.cert
keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey
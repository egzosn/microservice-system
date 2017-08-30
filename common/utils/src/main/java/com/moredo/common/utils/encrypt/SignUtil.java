package com.moredo.common.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zy on 2016/11/16.
 * 加密工具类
 */
public class SignUtil {
    /**
     * MD5加密在base64加密
     * @param dataCode   加密字符串
     * @return
     */
    public static String createdDataSign(String dataCode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(dataCode.getBytes("UTF-8"));
        byte[] digest = md.digest();
        String result = Base64Util.encode(digest);
        return result;
    }


}

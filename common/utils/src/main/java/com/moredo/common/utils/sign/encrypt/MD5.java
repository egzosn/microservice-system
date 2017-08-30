package com.moredo.common.utils.sign.encrypt;


import com.moredo.common.utils.StringUtil;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

/**
 * MD5签名工具
 */
public class MD5 {

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
        //拼接key
        text = text + key;
        return DigestUtils.md5DigestAsHex(getContentBytes(text, input_charset));
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param sign          签名结果
     * @param key           密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
        //拼接key
        text = text + key;
        String mysign = DigestUtils.md5DigestAsHex(getContentBytes(text, input_charset));
        //判断是否一样
        if (StringUtil.equals(mysign, sign)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @param content 需要加密串
     * @param charset 字符集
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    public static byte[] getContentBytes(String content, String charset) {
        if (StringUtil.isEmpty(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

}
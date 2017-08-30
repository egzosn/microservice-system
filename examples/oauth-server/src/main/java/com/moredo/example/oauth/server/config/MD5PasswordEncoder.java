package com.moredo.example.oauth.server.config;

import com.moredo.common.utils.encrypt.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MD5PasswordEncoder implements PasswordEncoder {

    public MD5PasswordEncoder() {

    }

    public String encode(CharSequence rawPassword) {
        return MD5Util.MD5(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            if (StringUtils.equals(MD5Util.MD5(rawPassword.toString()), encodedPassword)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

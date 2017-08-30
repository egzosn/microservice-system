package com.moredo.common.oauth;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户授权工具类
 *
 * @author 肖红星
 * @create 2016/10/10
 */
public class OAuthUtil {

//    /**
//     * 获取当前登录的用户
//     *
//     * @return
//     * @author 肖红星
//     * @create 2016/10/10
//     */
//    public static User getUser() {
//        try {
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            return user;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static String getUserId() {
        try {
            return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

}

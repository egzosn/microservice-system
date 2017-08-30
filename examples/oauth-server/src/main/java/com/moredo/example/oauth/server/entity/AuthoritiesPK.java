package com.moredo.example.oauth.server.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 用户权限信息表
 *
 * @author 肖红星
 * @create 2016/11/8
 */
@Embeddable
public class AuthoritiesPK implements Serializable {

    private static final long serialVersionUID = 334618264932354765L;

    //用户名
    private String username;
    //权限名称
    private String authority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

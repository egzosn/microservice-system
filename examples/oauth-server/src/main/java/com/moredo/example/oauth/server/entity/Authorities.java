package com.moredo.example.oauth.server.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户权限信息表
 *
 * @author 肖红星
 * @create 2016/11/8
 */
@Entity
@Table(name = "authorities")
public class Authorities implements Serializable {

    private static final long serialVersionUID = 4015227252204427427L;
    
    @EmbeddedId
    private AuthoritiesPK authoritiesPK;

    public AuthoritiesPK getAuthoritiesPK() {
        return authoritiesPK;
    }

    public void setAuthoritiesPK(AuthoritiesPK authoritiesPK) {
        this.authoritiesPK = authoritiesPK;
    }

}

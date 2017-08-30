package com.moredo.example.oauth.server.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户登录信息表
 *
 * @author 肖红星
 * @create 2016/11/8
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -3742673156251850570L;

    //用户ID
    @Id
    private long id;
    @Column(name = "user_id")
    private String userId;
    //用户名
    private String username;
    //登录密码
    private String password;
    //昵称
    private String nickname;
    //性别：1男 2女
    private int sex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

}

package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Comment;

/**
 * @author blackboy1987
 */
@Entity
@Comment("用户模块")
public class Admin extends BaseEntity{

    @Comment("用户名")
    @Column(length = 30,unique = true)
    @JsonView({BaseEntity.PageView.class})
    private String username;

    @Column(length = 100)
    @Comment("登录密码")
    private String password;

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


}

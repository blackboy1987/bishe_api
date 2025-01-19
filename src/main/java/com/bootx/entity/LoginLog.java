package com.bootx.entity;

import com.bootx.util.CommonUtils;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Comment;

@Entity
@Comment("登录日志日志")
public class LoginLog extends BaseEntity{

    @Comment("登录的ip")
    @JsonView({PageView.class})
    private String ip;

    @Comment("登录输入的用户名")
    @JsonView({PageView.class})
    private String username;

    @Comment("登录输入的密码")
    @JsonView({PageView.class})
    private String password;

    @Comment("登录的userAgent信息")
    private String userAgent;

    @Comment("登录的ua信息")
    private String ua;

    @Comment("登录的结果")
    @JsonView({PageView.class})
    private String result;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Transient
    @JsonView({PageView.class})
    public String getOs(){
        if(StringUtils.isNotBlank(userAgent)){
            return CommonUtils.getOperatingSystem(userAgent);
        }
        return "未知";
    }
    @Transient
    @JsonView({PageView.class})
    public String getBrowser(){
        if(StringUtils.isNotBlank(ua)){
            return CommonUtils.getBrowser(ua);
        }
        return "未知";
    }
}

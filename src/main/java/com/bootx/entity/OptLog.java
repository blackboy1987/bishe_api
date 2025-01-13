package com.bootx.entity;

import com.bootx.common.BaseAttributeConverter;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.HashMap;
import java.util.Map;

@Entity
@Comment("操作日志")
public class OptLog extends BaseEntity{

    public static final String AUDIT_LOG_ATTRIBUTE_NAME = OptLog.class.getName() + ".AUDIT_LOG";

    @Comment("操作名称")
    @JsonView({PageView.class})
    private String action;

    @Comment("操作者的ip")
    @JsonView({PageView.class})
    private String ip;

    @Comment("请求URL")
    @JsonView({PageView.class})
    private String requestUrl;

    @Comment("请求参数")
    @Convert(converter = ParameterConverter.class)
    private Map<String, String[]> parameters = new HashMap<String, String[]>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Transient
    @JsonView({PageView.class})
    public String getUsername(){
        if(admin==null){
            return null;
        }
        return admin.getUsername();
    }



    public static class ParameterConverter extends BaseAttributeConverter<Map<String,Object[]>>{}
}

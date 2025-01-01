package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

/**
 * @author blackboy1987
 */
@Entity
@Comment("用户模块")
public class Admin extends BaseEntity{

    @Comment("用户名")
    @Column(length = 30,unique = true)
    @JsonView({PageView.class})
    private String username;

    @Column(length = 100)
    @Comment("登录密码")
    private String password;


    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("部门")
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Transient
    @JsonView({PageView.class})
    public Long getDepartmentId() {
        if (department == null) {
            return null;
        }
        return department.getId();
    }

    @Transient
    @JsonView({PageView.class})
    public String getDepartmentName() {
        if (department == null) {
            return null;
        }
        return department.getName();
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * 重写equals方法
     *
     * @param obj
     *            对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * 重写hashCode方法
     *
     * @return HashCode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

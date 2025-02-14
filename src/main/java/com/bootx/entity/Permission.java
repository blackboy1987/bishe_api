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
@Comment("权限控制模块")
public class Permission extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @Comment("权限名称")
    @JsonView({PageView.class})
    private String name;

    @Comment("权限描述")
    @JsonView({PageView.class})
    private String memo;

    @Comment("权限资源。多个资源采用英文分号进行分割")
    @Column(length = 2000)
    @JsonView({PageView.class})
    private String resource;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonView({PageView.class})
    public String getMenuName(){
        if(menu==null){
            return null;
        }
        return menu.getName();
    }

    @JsonView({PageView.class})
    public Long getMenuId(){
        if(menu==null){
            return null;
        }
        return menu.getId();
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

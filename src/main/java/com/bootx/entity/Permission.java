package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Comment;

/**
 * @author blackboy1987
 */
@Entity
@Comment("权限控制模块")
public class Permission extends BaseEntity{

    @ManyToOne
    private Menu menu;

    @Comment("权限名称")
    @JsonView({PageView.class})
    private String name;

    @Comment("权限描述")
    @JsonView({PageView.class})
    private String memo;

    @Comment("权限操作符")
    @JsonView({PageView.class})
    private String action;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
}

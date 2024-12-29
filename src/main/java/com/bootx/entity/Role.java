package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Comment;

@Entity
public class Role extends BaseEntity{

    @Column(nullable = false)
    @Comment("角色名称")
    @JsonView({PageView.class})
    private String name;

    @Comment("角色描述")
    @JsonView({PageView.class})
    private String memo;

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
}

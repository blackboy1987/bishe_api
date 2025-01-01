package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Role extends BaseEntity{

    @Column(nullable = false)
    @Comment("角色名称")
    @JsonView({PageView.class})
    private String name;

    @Comment("角色描述")
    @JsonView({PageView.class})
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("部门")
    private Department department;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Admin> admins = new HashSet<>();

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

    public Department getDepartment() {
        return department;
    }

    public Set<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }
}

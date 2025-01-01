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
@Comment("角色")
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

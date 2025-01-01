package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author blackboy1987
 */
@Entity
@Comment("部门")
public class Department extends BaseEntity {

    public static final String TREE_PATH_SEPARATOR = ",";

    @OneToMany(mappedBy = "parent")
    private Set<Department> children = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("上级部门")
    private Department parent;

    @JsonView({PageView.class})
    @Comment("部门名称")
    private String name;

    @Column(name = "orders")
    @JsonView({PageView.class})
    @Comment("排序")
    private Integer order;

    @JsonView({PageView.class})
    @Comment("层级")
    private Integer grade;

    @Comment("层级树")
    private String treePath;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public Set<Department> getChildren() {
        return children;
    }

    public void setChildren(Set<Department> children) {
        this.children = children;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public Long[] getParentIds() {
        String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
        Long[] result = new Long[parentIds.length];
        for (int i = 0; i < parentIds.length; i++) {
            result[i] = Long.valueOf(parentIds[i]);
        }
        return result;
    }

    @Transient
    public List<Department> getParents() {
        List<Department> parents = new ArrayList<>();
        recursiveParents(parents, this);
        return parents;
    }

    private void recursiveParents(List<Department> parents, Department department) {
        if (department == null) {
            return;
        }
        Department parent = department.getParent();
        if (parent != null) {
            parents.addFirst(parent);
            recursiveParents(parents, parent);
        }
    }



    @Transient
    @JsonView({PageView.class})
    public Long getParentId() {
        if (parent == null) {
            return null;
        }
        return parent.getId();
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

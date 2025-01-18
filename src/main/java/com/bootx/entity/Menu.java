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
@Comment("菜单")
public class Menu extends BaseEntity {

    public static final String TREE_PATH_SEPARATOR = ",";

    @OneToMany(mappedBy = "parent")
    private Set<Menu> children = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu parent;

    @Comment("菜单名称")
    @JsonView({PageView.class})
    @Column(nullable = false)
    private String name;

    @Comment("路由")
    @JsonView({PageView.class})
    @Column(nullable = false)
    private String url;

    @Comment("菜单图标")
    @JsonView({PageView.class})
    private String icon;

    @Comment("菜单对应的组件")
    @Column(nullable = false)
    @JsonView({PageView.class})
    private String component;

    @Column(name = "orders")
    @JsonView({PageView.class})
    private Integer order;

    @JsonView({PageView.class})
    private Integer grade;

    private String treePath;

    @OneToMany(mappedBy = "menu",fetch = FetchType.LAZY)
    private Set<Permission> permissions = new HashSet<>();

    public Set<Menu> getChildren() {
        return children;
    }

    public void setChildren(Set<Menu> children) {
        this.children = children;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
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
    public List<Menu> getParents() {
        List<Menu> parents = new ArrayList<>();
        recursiveParents(parents, this);
        return parents;
    }

    private void recursiveParents(List<Menu> parents, Menu menu) {
        if (menu == null) {
            return;
        }
        Menu parent = menu.getParent();
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

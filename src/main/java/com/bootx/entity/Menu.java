package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Menu extends BaseEntity {

    public static final String TREE_PATH_SEPARATOR = ",";

    @OneToMany(mappedBy = "parent")
    private Set<Menu> children = new HashSet<>();

    @ManyToOne
    private Menu parent;

    @JsonView({PageView.class})
    private String name;

    @JsonView({PageView.class})
    private String url;

    @Column(name = "orders")
    @JsonView({PageView.class})
    private Integer order;

    @JsonView({PageView.class})
    private Integer grade;

    private String treePath;

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
            parents.add(0, parent);
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
}

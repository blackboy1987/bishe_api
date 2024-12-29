package com.bootx.service.impl;


import com.bootx.entity.Menu;
import com.bootx.repository.MenuRepository;
import com.bootx.service.MenuService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author blackboy1987
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private MenuRepository menuRepository;

    @Override
    public void save(Menu Menu) {
        setValue(Menu);
        menuRepository.save(Menu);
    }

    @Override
    public Menu findById(Long id) {
        if(id == null){
            return null;

        }
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public void delete(Menu Menu) {
        menuRepository.delete(Menu);
    }

    @Override
    public List<Menu> findList(Menu parent) {
        List<Menu> list = menuRepository.findAll((Specification<Menu>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restriction = criteriaBuilder.conjunction();
            if (parent != null) {
                restriction = criteriaBuilder.and(restriction, criteriaBuilder.like(root.get("parent"), "%," + parent.getId() + ",%"));
            }
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
            return restriction;
        });
        sort(list);
        return list;
    }

    @Override
    public List<Map<String, Object>> tree() {
        List<Map<String, Object>> roots = jdbcTemplate.queryForList("select id,name,(select count(id) from menu where menu.parent_id=parent.id) childCount from menu parent where parent_id is null order by orders asc ");
        return roots.stream().peek(item->{
            if(!Objects.equals(item.get("childCount")+"", "0")){
                item.put("children",getChildren(item.get("id")));
            }
            item.remove("childCount");
        }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> getChildren(Object parentId) {
        List<Map<String, Object>> children = jdbcTemplate.queryForList("select id,name,(select count(id) from menu where menu.parent_id=parent.id) childCount from menu parent where parent_id=? order by orders asc ",parentId);
        return children.stream().peek(item->{
            if(!Objects.equals(item.get("childCount")+"", "0")){
                item.put("children",getChildren(item.get("id")));
            }
            item.remove("childCount");
        }).collect(Collectors.toList());
    }


    private void setValue(Menu menu) {
        if (menu == null) {
            return;
        }
        Menu parent = menu.getParent();
        if (parent != null) {
            menu.setTreePath(parent.getTreePath() + parent.getId() + Menu.TREE_PATH_SEPARATOR);
        } else {
            menu.setTreePath(Menu.TREE_PATH_SEPARATOR);
        }
        menu.setGrade(menu.getParentIds().length);
    }


    private void sort(List<Menu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return;
        }
        final Map<Long, Integer> orderMap = new HashMap<>();
        for (Menu productCategory : menus) {
            orderMap.put(productCategory.getId(), productCategory.getOrder());
        }
        menus.sort((productCategory1, productCategory2) -> {
            Long[] ids1 = ArrayUtils.add(productCategory1.getParentIds(), productCategory1.getId());
            Long[] ids2 = ArrayUtils.add(productCategory2.getParentIds(), productCategory2.getId());
            Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
            Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
            CompareToBuilder compareToBuilder = new CompareToBuilder();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                Long id1 = iterator1.next();
                Long id2 = iterator2.next();
                Integer order1 = orderMap.get(id1);
                Integer order2 = orderMap.get(id2);
                compareToBuilder.append(order1, order2).append(id1, id2);
                if (!iterator1.hasNext() || !iterator2.hasNext()) {
                    compareToBuilder.append(productCategory1.getGrade(), productCategory2.getGrade());
                }
            }
            return compareToBuilder.toComparison();
        });
    }
}

package com.bootx.service.impl;


import com.bootx.entity.Department;
import com.bootx.repository.DepartmentRepository;
import com.bootx.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private DepartmentRepository departmentRepository;

    @Override
    public void save(Department department) {
        setValue(department);
        departmentRepository.save(department);
    }

    @Override
    public Department findById(Long id) {
        if(id == null){
            return null;

        }
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public void delete(Department Department) {
        departmentRepository.delete(Department);
    }

    @Override
    public List<Department> findList(Department parent) {
        List<Department> list = departmentRepository.findAll((Specification<Department>) (root, criteriaQuery, criteriaBuilder) -> {
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
        List<Map<String, Object>> roots = jdbcTemplate.queryForList("select id,name,(select count(id) from department where department.parent_id=parent.id) childCount from department parent where parent_id is null order by orders asc ");
        return roots.stream().peek(item->{
            if(!Objects.equals(item.get("childCount")+"", "0")){
                item.put("children",getChildren(item.get("id")));
            }
            item.remove("childCount");
        }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> getChildren(Object parentId) {
        List<Map<String, Object>> children = jdbcTemplate.queryForList("select id,name,(select count(id) from department where department.parent_id=parent.id) childCount from department parent where parent_id=? order by orders asc ",parentId);
        return children.stream().peek(item->{
            if(!Objects.equals(item.get("childCount")+"", "0")){
                item.put("children",getChildren(item.get("id")));
            }
            item.remove("childCount");
        }).collect(Collectors.toList());
    }


    private void setValue(Department department) {
        if (department == null) {
            return;
        }
        Department parent = department.getParent();
        if (parent != null) {
            department.setTreePath(parent.getTreePath() + parent.getId() + Department.TREE_PATH_SEPARATOR);
        } else {
            department.setTreePath(Department.TREE_PATH_SEPARATOR);
        }
        department.setGrade(department.getParentIds().length);
    }


    private void sort(List<Department> departments) {
        if (CollectionUtils.isEmpty(departments)) {
            return;
        }
        final Map<Long, Integer> orderMap = new HashMap<>();
        for (Department productCategory : departments) {
            orderMap.put(productCategory.getId(), productCategory.getOrder());
        }
        departments.sort((department1, department2) -> {
            Long[] ids1 = ArrayUtils.add(department1.getParentIds(), department1.getId());
            Long[] ids2 = ArrayUtils.add(department2.getParentIds(), department2.getId());
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
                    compareToBuilder.append(department1.getGrade(), department2.getGrade());
                }
            }
            return compareToBuilder.toComparison();
        });
    }
}

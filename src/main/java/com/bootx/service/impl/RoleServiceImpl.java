package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Department;
import com.bootx.entity.Role;
import com.bootx.repository.RoleRepository;
import com.bootx.service.RoleService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author blackboy1987
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role find(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            roleRepository.deleteById(id);
        }
    }

    @Override
    public Page<Role> findPage(Pageable pageable, String name, String memo, Date beginDate , Date endDate, Department department) {
        org.springframework.data.domain.Page<Role> all = roleRepository.findAll((Specification<Role>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restriction = criteriaBuilder.conjunction();
            if(StringUtils.isNotBlank(name)){
                restriction = criteriaBuilder.and(restriction, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
            }
            if (department != null) {
                restriction = criteriaBuilder.and(criteriaBuilder.equal(root.get("department"), department));
            }
            if(StringUtils.isNotBlank(memo)){
                restriction = criteriaBuilder.and(restriction, criteriaBuilder.like(root.get("memo"), "%"+name+"%"));
            }
            if(beginDate != null){
                restriction = criteriaBuilder.and(restriction, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
            }
            if(endDate != null){
                restriction = criteriaBuilder.and(restriction, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            return restriction;
        }, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
        return new Page<>(all.getContent(),all.getTotalElements(),pageable);
    }

    @Override
    public List<Role> findListByIds(Long[] ids) {
        List<Role> roles = new ArrayList<>();
        Iterable<Role> allById = roleRepository.findAllById(Arrays.stream(ids).toList());
        allById.forEach(roles::add);
        return roles;
    }
}

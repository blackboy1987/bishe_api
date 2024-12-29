package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
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

import java.util.Date;

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
    public Page<Role> findPage(Pageable pageable, String name, String memo, Date beginDate , Date endDate) {
        org.springframework.data.domain.Page<Role> all = roleRepository.findAll((Specification<Role>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            if(StringUtils.isNotBlank(name)){
                conjunction = criteriaBuilder.and(conjunction, criteriaBuilder.like(root.get("name"), "%"+name+"%"));
            }
            if(StringUtils.isNotBlank(memo)){
                conjunction = criteriaBuilder.and(conjunction, criteriaBuilder.like(root.get("memo"), "%"+name+"%"));
            }
            if(beginDate != null){
                conjunction = criteriaBuilder.and(conjunction, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
            }
            if(endDate != null){
                conjunction = criteriaBuilder.and(conjunction, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            return conjunction;
        }, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
        return new Page<>(all.getContent(),all.getTotalElements(),pageable);
    }
}

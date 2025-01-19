package com.bootx.service.impl;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Admin;
import com.bootx.entity.Menu;
import com.bootx.entity.Permission;
import com.bootx.repository.PermissionRepository;
import com.bootx.service.PermissionService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author blackboy1987
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }

    @Override
    public Permission findById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public void delete(Permission permission) {
        permissionRepository.delete(permission);
    }

    @Override
    public Page<Permission> findPage(Pageable pageable, Menu menu) {
        org.springframework.data.domain.Page<Permission> all = permissionRepository.findAll((Specification<Permission>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restriction = criteriaBuilder.conjunction();
            if (menu != null) {
                restriction = criteriaBuilder.and(criteriaBuilder.equal(root.get("menu"), menu));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            return restriction;
        }, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
        return new Page<>(all.getContent(),all.getTotalElements(), pageable);
    }

    @Override
    public List<Permission> findByIds(List<Long> ids) {
        List<Permission> all = new ArrayList<>();
        permissionRepository.findAllById(ids).forEach(all::add);
        return all;
    }

    @Override
    public Set<String> getAuthority(Admin admin) {
        if(admin==null){
            return Collections.emptySet();
        }
        List<Map<String,Object>> authorities = jdbcTemplate.queryForList("select resource from permission where id in (select permissions_id from role_permissions where roles_id in (select roles_id from admin_roles where admins_id=?))",admin.getId());
        Set<String> authority = new HashSet<>();
        authorities.forEach(row->{
            String resource = row.get("resource")+"";
            String[] split = resource.split(";");
            for (String s : split) {
                if(StringUtils.isNotBlank(s) && !StringUtils.equalsAnyIgnoreCase(s,"null")){
                    authority.add(s);
                }
            }
        });
        return authority;
    }

}

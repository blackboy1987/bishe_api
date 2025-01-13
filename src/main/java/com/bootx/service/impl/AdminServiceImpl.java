package com.bootx.service.impl;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.entity.Department;
import com.bootx.repository.AdminRepository;
import com.bootx.service.AdminService;
import com.bootx.util.JWTUtils;
import com.bootx.util.WebUtils;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author blackboy1987
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminRepository adminRepository;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin update(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void lock(Admin admin) {
        int errorCount = 0;
        String cacheKey = "login:lock:" + admin.getUsername();
        try {
            errorCount = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(cacheKey)));
        }catch (Exception ignored){
            stringRedisTemplate.opsForValue().set(cacheKey,"0",30, TimeUnit.MINUTES);
        }
        if(errorCount>=5){
            admin.setIsLocked(true);
            update(admin);
        }
        stringRedisTemplate.opsForValue().increment("login:lock:"+admin.getUsername());
    }

    @Override
    public void unLock(Admin admin) {
        String cacheKey = "login:lock:" + admin.getUsername();
        admin.setIsLocked(false);
        update(admin);
        stringRedisTemplate.delete(cacheKey);
    }

    @Override
    public boolean existUsername(String username) {
        List<Admin> list = adminRepository.findByUsername(username);
        return  list != null && !list.isEmpty();
    }

    @Override
    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public boolean uniqueUsername(String username, Long id) {
        List<Admin> list = adminRepository.findByUsername(username);
        if(list==null || list.isEmpty()){
            return true;
        }
        list = list.stream().filter(item-> !item.getId().equals(id)).toList();
        if(list.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public void delete(Admin admin) {
        adminRepository.delete(admin);
    }

    @Override
    public Page<Admin> findList(Pageable pageable, String username, Date beginDate, Date endDate, Department department) {
        org.springframework.data.domain.Page<Admin> all = adminRepository.findAll((Specification<Admin>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restriction = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(username)) {
                restriction = criteriaBuilder.and(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            if (department != null) {
                restriction = criteriaBuilder.and(criteriaBuilder.equal(root.get("department"), department));
            }
            if (beginDate != null) {
                restriction = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
            }
            if (endDate != null) {
                restriction = criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            return restriction;
        }, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
        return new Page<>(all.getContent(),all.getTotalElements(), pageable);
    }

    @Override
    public Admin findByUsername(String username) {
        List<Admin> byUsername = adminRepository.findByUsername(username);
        if(byUsername.isEmpty()){
            return null;
        }
        return byUsername.getFirst();
    }

    @Override
    public Admin getCurrent() {
        try {
            String token = WebUtils.getRequest().getHeader("token");
            String id = JWTUtils.getId(token);
            return findById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admin getCurrent(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            String id = JWTUtils.getId(token);
            return findById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Department;
import com.bootx.entity.Role;

import java.util.Date;
import java.util.List;

/**
 * @author blackboy1987
 */
public interface RoleService {
    void save(Role role);

    Role find(Long id);

    void update(Role role);

    void delete(Long[] ids);

    Page<Role> findPage(Pageable pageable, String name, String memo, Date beginDate , Date endDate, Department department);

    List<Role> findListByIds(Long[] roleIds);
}

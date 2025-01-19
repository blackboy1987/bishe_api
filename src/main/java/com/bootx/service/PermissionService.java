package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Admin;
import com.bootx.entity.Menu;
import com.bootx.entity.Permission;

import java.util.List;
import java.util.Set;

/**
 * @author blackboy1987
 */
public interface PermissionService {

    void save(Permission permission);

    Permission findById(Long id);

    void delete(Long id);

    void delete(Permission permission);

    Page<Permission> findPage(Pageable pageable, Menu menu);

    List<Permission> findByIds(List<Long> ids);

    Set<String> getAuthority(Admin admin);
}

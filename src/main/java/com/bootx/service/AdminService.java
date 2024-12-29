package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Admin;

import java.util.Date;

/**
 * @author blackboy1987
 */
public interface AdminService {

    void save(Admin admin);

    boolean existUsername(String username);

    Admin findById(Long id);

    boolean uniqueUsername(String username, Long id);

    void delete(Long id);

    void delete(Admin admin);

    Page<Admin> findList(Pageable pageable, String username, Date beginDate, Date endDate);
}

package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Admin;
import com.bootx.entity.Department;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

/**
 * @author blackboy1987
 */
public interface AdminService {

    /**
     * 保存管理员
     * @param admin
     *      管理员
     * @return
     *      管理员
     */
    Admin save(Admin admin);

    /**
     * 判断用户名是否存在
     * @param username
     *      用户名
     * @return
     *      是否存在
     */
    boolean existUsername(String username);

    /**
     * 根据id查询管理员
     * @param id
     *      id
     * @return
     *      管理员
     */
    Admin findById(Long id);

    /**
     * 判断用户名是否唯一
     * @param username
     *      用户名
     * @param id
     *      id
     * @return
     *      是否唯一
     */
    boolean uniqueUsername(String username, Long id);

    /**
     * 删除管理员
     * @param id
     *      管理员id
     */
    void delete(Long id);

    /**
     * 删除管理员
     * @param admin
     *      管理员
     */
    void delete(Admin admin);

    /**
     * 分页查询管理员
     * @param pageable
     *      分页对象
     * @param username
     *      用户名
     * @param beginDate
     *      开始时间
     * @param endDate
     *      结束时间
     * @param department
     *      部门
     * @return
     *      分页数据
     */
    Page<Admin> findList(Pageable pageable, String username, Date beginDate, Date endDate, Department department);

    Admin findByUsername(String username);

    Admin getCurrent();

    Admin getCurrent(HttpServletRequest request);

    Admin update(Admin admin);

    void lock(Admin admin);

    void unLock(Admin admin);
}

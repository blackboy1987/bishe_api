package com.bootx.repository;

import com.bootx.entity.Admin;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author blackboy1987
 */
@Repository
public interface AdminRepository extends CrudRepository<Admin,Long>, PagingAndSortingRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {

    /**
     * 根据用户名查询用户
     * @param username
     *      用户名
     * @return
     *      用户列表
     */
    List<Admin> findByUsername(String username);

}

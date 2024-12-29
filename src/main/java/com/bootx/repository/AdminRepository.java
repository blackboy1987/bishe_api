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

    List<Admin> findByUsername(String username);

}

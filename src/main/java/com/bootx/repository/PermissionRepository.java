package com.bootx.repository;

import com.bootx.entity.Permission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author blackboy1987
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission,Long>, PagingAndSortingRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {


}

package com.bootx.repository;

import com.bootx.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>, PagingAndSortingRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}

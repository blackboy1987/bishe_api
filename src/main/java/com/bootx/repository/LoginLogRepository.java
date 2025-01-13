package com.bootx.repository;

import com.bootx.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author blackboy1987
 */
@Repository
public interface LoginLogRepository extends CrudRepository<LoginLog,Long>, PagingAndSortingRepository<LoginLog, Long>, JpaSpecificationExecutor<LoginLog> {
}

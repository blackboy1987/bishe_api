package com.bootx.repository;

import com.bootx.entity.OptLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author blackboy1987
 */
@Repository
public interface OptLogRepository extends CrudRepository<OptLog,Long>, PagingAndSortingRepository<OptLog, Long>, JpaSpecificationExecutor<OptLog> {
}

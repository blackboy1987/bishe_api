package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.OptLog;

import java.util.Date;

/**
 * @author blackboy1987
 */
public interface OptLogService {


    void save(OptLog optLog);

    Page<OptLog> findPage(Pageable pageable, String username, Date beginDate, Date endDate, String action);
}

package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.LoginLog;
import com.bootx.entity.OptLog;

import java.util.Date;

/**
 * @author blackboy1987
 */
public interface LoginLogService {


    void save(LoginLog loginLog);

    Page<LoginLog> findPage(Pageable pageable, String username, Date beginDate, Date endDate, String password);
}

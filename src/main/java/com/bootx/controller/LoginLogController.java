package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.service.LoginLogService;
import com.bootx.service.OptLogService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author blackboy1987
 */
@RestController
@RequestMapping("/api/login_log")
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list(Pageable pageable, String username, Date beginDate, Date endDate, String password) {
        return Result.success(loginLogService.findPage(pageable,username,beginDate,endDate,password));
    }
}

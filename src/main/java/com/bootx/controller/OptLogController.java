package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
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
@RequestMapping("/api/opt_log")
public class OptLogController {

    @Resource
    private OptLogService optLogService;

    @PostMapping("/list")
    @JsonView({BaseEntity.PageView.class})
    public Result list(Pageable pageable, String username, Date beginDate, Date endDate, String action) {
        return Result.success(optLogService.findPage(pageable,username,beginDate,endDate,action));
    }
}

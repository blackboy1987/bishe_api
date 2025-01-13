package com.bootx.service.impl;


import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.OptLog;
import com.bootx.repository.OptLogRepository;
import com.bootx.service.OptLogService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author blackboy1987
 */
@Service
public class OptLogServiceImpl implements OptLogService {

    @Resource
    private OptLogRepository optLogRepository;

    @Override
    public void save(OptLog optLog) {
        optLogRepository.save(optLog);
    }

    @Override
    public Page<OptLog> findPage(Pageable pageable, String username, Date beginDate, Date endDate, String action) {
        org.springframework.data.domain.Page<OptLog> all = optLogRepository.findAll((Specification<OptLog>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restriction = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(username)) {
                //restriction = criteriaBuilder.and(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            if (action != null) {
                restriction = criteriaBuilder.and(criteriaBuilder.equal(root.get("action"), action));
            }
            if (beginDate != null) {
                restriction = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), beginDate));
            }
            if (endDate != null) {
                restriction = criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            return restriction;
        }, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
        return new Page<>(all.getContent(),all.getTotalElements(), pageable);
    }
}

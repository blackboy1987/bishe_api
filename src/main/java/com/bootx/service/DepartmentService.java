package com.bootx.service;

import com.bootx.entity.Department;
import com.bootx.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * @author blackboy1987
 */
public interface DepartmentService {

    void save(Department department);

    Department findById(Long id);

    void delete(Long id);

    void delete(Department department);

    List<Department> findList(Department parent);

    List<Map<String, Object>> tree();
}

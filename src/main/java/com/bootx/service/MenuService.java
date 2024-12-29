package com.bootx.service;

import com.bootx.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * @author blackboy1987
 */
public interface MenuService {

    void save(Menu menu);

    Menu findById(Long id);

    void delete(Long id);

    void delete(Menu menu);

    List<Menu> findList(Menu parent);

    List<Map<String, Object>> tree();
}

package com.upyourassapp.service;

import java.util.Map;

/**
 * @author Kirill Popov
 */
public interface Service<T> {
    Map<Integer, T> list();

    T find(Integer id);

    Integer save(T t);

    T update(Integer id, T t);

    Boolean delete(Integer id);
}

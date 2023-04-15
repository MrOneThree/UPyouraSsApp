package com.upyourassapp.storage;

import java.util.Map;

/**
 * @author Kirill Popov
 */
public interface Storage<T> {
    Map<Integer, T> list();

    Integer put(T t);

    T update(Integer id, T t);

    T fetchById(Integer id);

    Boolean delete(Integer id);
}

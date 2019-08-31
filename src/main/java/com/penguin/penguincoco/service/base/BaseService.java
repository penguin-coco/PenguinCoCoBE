package com.penguin.penguincoco.service.base;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, ID extends Serializable> {

    T save(T t);

    Iterable<T> saveAll(Iterable<T> entities);

    T findById(ID id) throws EntityNotFoundException;

    List<T> findAll();

    void delete(ID id) throws EntityNotFoundException;

    void delete(T t) throws EntityNotFoundException;

    void deleteAll();

}

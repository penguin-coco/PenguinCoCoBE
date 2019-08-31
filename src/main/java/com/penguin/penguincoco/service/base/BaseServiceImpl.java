package com.penguin.penguincoco.service.base;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    public abstract BaseRepository<T, ID> getBaseRepository();

    @Override
    public T save(T t) {
        return getBaseRepository().save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
        return getBaseRepository().saveAll(entities);
    }

    @Override
    public T findById(ID id) throws EntityNotFoundException {
        return getBaseRepository().findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<T> findAll() {
        return getBaseRepository().findAll();
    }

    @Override
    public void delete(ID id) {
        getBaseRepository().deleteById(id);
    }

    @Override
    public void delete(T t) {
        getBaseRepository().delete(t);
    }

    @Override
    public void deleteAll() {
        getBaseRepository().deleteAll();
    }
}

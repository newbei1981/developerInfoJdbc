package com.newbei.developerJdbc.repository;

import java.sql.SQLException;
import java.util.List;


public interface GenericRepository<T,ID> {
    List<T> getAll();
    T getById(ID id);
    T save(T t);
    T update(T t);
    T deleteById(ID id);
}
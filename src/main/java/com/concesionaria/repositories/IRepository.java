package com.concesionaria.repositories;

import java.util.List;

public interface IRepository<T> {
    List<T> findAll();
    T findById(Long id);
}
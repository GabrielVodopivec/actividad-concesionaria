package com.concesionaria.services;

import java.util.List;

public interface IService<T> {
    List<T> findAll();
    T findOne(Long id);
}
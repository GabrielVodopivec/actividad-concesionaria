package com.concesionaria.repositories;

import java.time.LocalDate;
import java.util.List;

public interface IRepository<T> {
    List<T> findAll();
    T findById(Long id);

    T createOrUpdate(T t);

    List<T> findByDate(LocalDate initialDate, LocalDate finalDate);
}
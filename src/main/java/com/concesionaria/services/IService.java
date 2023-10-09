package com.concesionaria.services;

import java.time.LocalDate;
import java.util.List;

public interface IService<T> {
    List<T> findAll();

    T findOne(Long id);

    T createOrUpdate(T t);

    List<T> findByDate(LocalDate since, LocalDate to);

    List<T> filterByPrice(Integer since, Integer to, String currency);

    void deleteOne(Long id);
}
package com.concesionaria.services;

import java.time.LocalDate;
import java.util.List;

public interface IService<T, V> {
    List<T> findAll();
    V findOne(Long id);
    V createOrUpdate(V v);
    List<T> findByDate(LocalDate since, LocalDate to);
    List<T> filterByPrice(Integer since, Integer to, String currency);
}
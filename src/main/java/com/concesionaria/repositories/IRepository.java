package com.concesionaria.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    T createOrUpdate(T t);

    List<T> findByDate(LocalDate since, LocalDate to);

    List<T> findByPrice(Integer since, Integer to, String currency);

    T deleteOne(Long id);
}
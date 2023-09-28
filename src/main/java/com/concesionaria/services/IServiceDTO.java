package com.concesionaria.services;

import java.util.List;

public interface IServiceDTO<T> {
    List<T> findAll();
}
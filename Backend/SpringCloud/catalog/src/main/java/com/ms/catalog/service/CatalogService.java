package com.ms.catalog.service;

import com.ms.catalog.entity.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalog();
}

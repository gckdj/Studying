package com.ms.catalog.controller;

import com.ms.catalog.entity.CatalogEntity;
import com.ms.catalog.service.CatalogService;
import com.ms.catalog.vo.ResponseCatalog;
import com.netflix.discovery.converters.Auto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("catalog-service")
public class CatalogController {
    Environment env;
    CatalogService catalogService;

    @Autowired
    public CatalogController(Environment env, CatalogService catalogService) {
        this.env = env;
        this.catalogService = catalogService;
    }

    @GetMapping("heath-check")
    public String status() {
        return String.format("It's Working in Catalog Service on Port %s",
                env.getProperty("local.service.port"));
    }

    @PostMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        Iterable<CatalogEntity> allCatalogs = catalogService.getAllCatalog();

        ModelMapper mapper = new ModelMapper();
        List<ResponseCatalog> result = new ArrayList<>();
        allCatalogs.forEach(v -> {
            result.add(mapper.map(v, ResponseCatalog.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

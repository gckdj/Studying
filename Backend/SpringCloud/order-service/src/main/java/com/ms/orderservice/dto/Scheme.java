package com.ms.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Scheme {
    private String type;
    private List<Field> fields;
    private boolean optional;
    private String name;
}

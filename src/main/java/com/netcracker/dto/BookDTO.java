package com.netcracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {
    private String name;
    private Double price;
    private String storage;
    private Integer quantity;
}

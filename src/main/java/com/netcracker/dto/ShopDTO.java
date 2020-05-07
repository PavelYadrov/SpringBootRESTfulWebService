package com.netcracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopDTO {

    private String name;
    private String location;
    private Double commission;
}

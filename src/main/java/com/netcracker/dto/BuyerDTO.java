package com.netcracker.dto;

import com.netcracker.model.Buyer;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BuyerDTO {

    private String lastname;

    private String district;

    private Double discount;

}

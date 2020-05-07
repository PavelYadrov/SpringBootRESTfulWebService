package com.netcracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
public class PurchaseDTO {

    private Date date;
    private Long shop_id;
    private Long buyer_id;
    private Long book_id;
    private Integer quantity;
    private Double sum;

}

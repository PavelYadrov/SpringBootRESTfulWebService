package com.netcracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Date date;

    @NonNull
    private Long shop_id;

    @NonNull
    private Long buyer_id;

    @NonNull
    private Long book_id;

    @NonNull
    private Integer quantity;

    private Double sum;
}

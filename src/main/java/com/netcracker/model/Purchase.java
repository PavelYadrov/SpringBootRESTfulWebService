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

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    @NonNull
    private Integer quantity;

    @NonNull
    private Double sum;


}

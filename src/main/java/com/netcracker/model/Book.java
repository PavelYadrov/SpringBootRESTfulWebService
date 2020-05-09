package com.netcracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Double price;

    @NonNull
    private String storage;

    @NonNull
    private Integer quantity;

    @JsonIgnore
    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER)
    private Collection<Purchase> purchases;
}

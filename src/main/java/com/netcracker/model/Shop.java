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
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String location;

    @NonNull
    private Double commission;

    @JsonIgnore
    @OneToMany(mappedBy = "shop",fetch = FetchType.EAGER)
    private Collection<Purchase> purchases;

}

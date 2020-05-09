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
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String lastname;

    @NonNull
    private String district;

    @NonNull
    private Double discount;

    @JsonIgnore
    @OneToMany(mappedBy = "buyer",fetch = FetchType.EAGER)
    private Collection<Purchase> purchases;

}

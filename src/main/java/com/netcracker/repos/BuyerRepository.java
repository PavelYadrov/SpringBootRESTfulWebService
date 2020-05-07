package com.netcracker.repos;

import com.netcracker.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface BuyerRepository extends JpaRepository<Buyer,Long> {

    Buyer findByLastname(String lastname);

    @Query(value = "select b.district from buyer b",nativeQuery = true)
    Set<String> findAllDistrict();

    @Query(value = "select b.lastname,b.discount from buyer b where b.district='Nijegorodskii'",nativeQuery = true)
    List<Object[]> findByDistrict();
}

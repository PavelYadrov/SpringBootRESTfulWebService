package com.netcracker.repos;

import com.netcracker.model.Buyer;
import com.netcracker.view.buyer.BuyerViewV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface BuyerRepository extends JpaRepository<Buyer,Long> {

    Buyer findByLastname(String lastname);

    @Query(value = "select distinct b.district from buyer b",nativeQuery = true)
    Set<String> findAllDistrict();

    @Query(value = "select b.lastname,b.discount from buyer b where b.district='Nijegorodskii'",nativeQuery = true)
    List<BuyerViewV1> findByDistrict();
}

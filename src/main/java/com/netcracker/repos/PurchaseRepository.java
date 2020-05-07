package com.netcracker.repos;

import com.netcracker.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    @Query(value = "select to_number(to_char(pu.date,'MM'),'99') from purchase pu",nativeQuery = true)
    Set<Integer> findAllMonth();

    List<Purchase> findAllBySumGreaterThan(Double sum);

    @Query(value = "select b.lastname,b.district,pu.date from purchase pu join buyer b on(b.id=pu.buyer_id) \n" +
            "join shop s on(s.id=pu.shop_id) where to_number(to_char(pu.date,'MM'),'99')>:month and b.district=s.location\n" +
            "order by pu.date",nativeQuery = true)
    List<Object[]> findAllPurchasesAfterMonthAndSameLocs(@Param("month") Long month);







}

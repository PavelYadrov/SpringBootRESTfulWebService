package com.netcracker.repos;

import com.netcracker.model.Purchase;
import com.netcracker.view.purchase.PurchaseViewDetailedInfo;
import com.netcracker.view.purchase.PurchaseViewGreaterSum;
import com.netcracker.view.purchase.PurchaseViewNameLocDate;
import com.netcracker.view.purchase.PurchaseViewNamesAndShops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    @Query(value = "select to_number(to_char(pu.date,'MM'),'99') from purchase pu",nativeQuery = true)
    Set<Integer> findAllMonth();

    //5.1
    @Query(value = "select pu.id,b.lastname,pu.date from purchase pu \n" +
            "join buyer b on(pu.shop_id=b.id)\n" +
            "where sum>:sum",nativeQuery = true)
    List<PurchaseViewGreaterSum> findAllBySumGreaterThan(@Param("sum")Double sum);

    //5.2
    @Query(value = "select b.lastname,b.district,pu.date from purchase pu join buyer b on(b.id=pu.buyer_id) \n" +
            "join shop s on(s.id=pu.shop_id) where to_number(to_char(pu.date,'MM'),'99')>:month and b.district=s.location\n" +
            "order by pu.date",nativeQuery = true)
    List<PurchaseViewNameLocDate> findAllPurchasesAfterMonthAndSameLocs(@Param("month") Long month);

    //4.1
    @Query(value = "select s.name,b.lastname from purchase pu \n" +
            "join shop s on(pu.shop_id=s.id)\n" +
            "join buyer b on(pu.shop_id=b.id)",nativeQuery = true)
    List<PurchaseViewNamesAndShops> findAllNamesAndShops();

    //4.2
    @Query(value = "select pu.date,b.lastname,b.discount,bo.name,pu.quantity from purchase pu \n" +
            "join book bo on(pu.shop_id=bo.id)\n" +
            "join buyer b on(pu.shop_id=b.id)",nativeQuery = true)
    List<PurchaseViewDetailedInfo> getDetailedPurchaseInfo();

}

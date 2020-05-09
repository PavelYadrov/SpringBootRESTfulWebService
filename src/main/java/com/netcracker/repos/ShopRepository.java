package com.netcracker.repos;

import com.netcracker.dto.ShopDTO;
import com.netcracker.model.Shop;
import com.netcracker.view.shop.ShopViewWithParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedNativeQuery;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findByName(String name);

    @Query(value = "select s.name from shop s where s.location=:first or s.location=:second",nativeQuery = true)
    List<String> findNameByLocations(@Param("first") String first,
                                     @Param("second") String second);

    @Query(value = "select s.id,s.name,s.location,s.commission from shop s \n" +
            "join purchase pu on (s.id=pu.shop_id)\n" +
            "join buyer b on(b.id=pu.buyer_id)\n" +
            "where s.location!=:location and b.discount between :lower and :higher", nativeQuery = true)
    List<ShopViewWithParams> findAllShopsOmitLocationWithDiscountInterval(@Param("location") String location,
                                                                          @Param("lower") Integer lower,
                                                                          @Param("higher") Integer higher);
}

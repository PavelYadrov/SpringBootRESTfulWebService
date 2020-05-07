package com.netcracker.service;

import com.netcracker.dto.ShopDTO;
import com.netcracker.model.Shop;
import com.netcracker.repos.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop findById(Long id){
        Shop shop = shopRepository.findById(id).orElse(null);
        log.info("IN findById - id {}, shop {}",id,shop);
        return shop;
    }

    public void delete(Shop shop){
        shopRepository.delete(shop);
        log.info("Successfully deleted {}:",shop);
    }

    public void update(ShopDTO shopDTO, Shop shop){
        if (shopDTO.getName()!=null) shop.setName(shopDTO.getName());
        if (shopDTO.getLocation()!=null) shop.setLocation(shopDTO.getLocation());
        if (shopDTO.getCommission()!=null) shop.setCommission(shopDTO.getCommission());
    }
    public void change(Shop shopOld,Shop shopNew){
        shopOld.setName(shopNew.getName());
        shopOld.setCommission(shopNew.getCommission());
        shopOld.setLocation(shopNew.getLocation());
    }

    public List<Shop> getAllShops(){
        List<Shop> shops =shopRepository.findAll();
        log.info("IN getAllshops - shops: {}",shops);
        return shops;
    }

    public List<String> findShopNamesWithinTwoLocations(String firstLoc,String secondLoc){
        List<String> names = shopRepository.findNameByLocations(firstLoc,secondLoc);
        log.info("Founded names : {}",names);
        return names;
    }

    public List<Shop> getAllShopsOmitLocationWithDiscountInterval(String location, Integer lower,Integer higher){
        if (lower==null){
            lower=1;
            higher=100;
            return shopRepository.findAllShopsOmitLocationWithDiscountInterval(location,lower,higher);
        }
        if(higher==null){
            higher=100;
            return shopRepository.findAllShopsOmitLocationWithDiscountInterval(location,lower,higher);
        }
        return shopRepository.findAllShopsOmitLocationWithDiscountInterval(location,lower,higher);

    }
}


package com.netcracker.service;

import com.netcracker.dto.BuyerDTO;
import com.netcracker.model.Buyer;
import com.netcracker.repos.BuyerRepository;
import com.netcracker.view.buyer.BuyerViewV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class BuyerService {

    private final BuyerRepository buyerRepository;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public Buyer findById(Long id){
        Buyer buyer = buyerRepository.findById(id).orElse(null);
        log.info("IN findById - id {}, buyer {}",id,buyer);
        return buyer;
    }

    public void delete(Buyer buyer){
        buyerRepository.delete(buyer);
        log.info("Successfully deleted {}:",buyer);
    }

    public void update(BuyerDTO buyerDTO,Buyer buyer){
            if (buyerDTO.getLastname()!=null) buyer.setLastname(buyerDTO.getLastname());
            if (buyerDTO.getDiscount()!=null) buyer.setDiscount(buyerDTO.getDiscount());
            if (buyerDTO.getDistrict()!=null) buyer.setDistrict(buyerDTO.getDistrict());
    }
    public void change(Buyer buyerOld,Buyer buyerNew){
        buyerOld.setLastname(buyerNew.getLastname());
        buyerOld.setDistrict(buyerNew.getDistrict());
        buyerOld.setDiscount(buyerNew.getDiscount());
    }

    public List<Buyer> getAllBuyers(){
        List<Buyer> buyers =buyerRepository.findAll();
        log.info("IN getAllBuyers - buyers: {}",buyers);
        return buyers;
    }

    public Set<String> getAllDistricts(){
        Set<String> dists = buyerRepository.findAllDistrict();
        log.info("IN  getAllDistricts districts found : {}",dists);
        return dists;
    }

    public List<BuyerViewV1> getAllNamesAndDiscounts(){
        List<BuyerViewV1> list = buyerRepository.findByDistrict();
        log.info("IN getAllNamesAndDiscounts : {}",list);
        return list;
    }
}



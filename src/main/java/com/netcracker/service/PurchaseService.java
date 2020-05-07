package com.netcracker.service;


import com.netcracker.dto.PurchaseDTO;
import com.netcracker.model.Book;
import com.netcracker.model.Buyer;
import com.netcracker.model.Purchase;
import com.netcracker.repos.BookRepository;
import com.netcracker.repos.BuyerRepository;
import com.netcracker.repos.PurchaseRepository;
import com.netcracker.repos.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ShopRepository shopRepository;
    private final BuyerRepository buyerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, ShopRepository shopRepository,
                           BuyerRepository buyerRepository, BookRepository bookRepository) {
        this.purchaseRepository = purchaseRepository;
        this.shopRepository = shopRepository;
        this.buyerRepository = buyerRepository;
        this.bookRepository=bookRepository;
    }

    public Purchase findById(Long id){
        Purchase purchase = purchaseRepository.findById(id).orElse(null);
        log.info("IN findById - id {}, purchase {}",id,purchase);
        return purchase;
    }

    public void delete(Purchase purchase){
        purchaseRepository.delete(purchase);
        log.info("Successfully deleted {}:",purchase);
    }

    public void update(PurchaseDTO purchaseDTO, Purchase purchase){
        if (purchaseDTO.getBook_id()!=null) purchase.setBook_id(purchaseDTO.getBook_id());
        if (purchaseDTO.getBuyer_id()!=null) purchase.setBuyer_id(purchaseDTO.getBuyer_id());
        if (purchaseDTO.getDate()!=null) purchase.setDate(purchaseDTO.getDate());
        if (purchaseDTO.getQuantity()!=null) purchase.setQuantity(purchaseDTO.getQuantity());
        if (purchaseDTO.getShop_id()!=null) purchase.setShop_id(purchaseDTO.getShop_id());
        if (purchaseDTO.getSum()!=null) purchase.setSum(purchaseDTO.getSum());
    }
    public void change(Purchase purchaseOld,Purchase purchaseNew){
        purchaseOld.setSum(purchaseNew.getSum());
        purchaseOld.setShop_id(purchaseNew.getShop_id());
        purchaseOld.setQuantity(purchaseNew.getQuantity());
        purchaseOld.setDate(purchaseNew.getDate());
        purchaseOld.setBuyer_id(purchaseNew.getBuyer_id());
        purchaseOld.setBook_id(purchaseNew.getBook_id());
    }

    public List<Purchase> getAllPurchases(){
        List<Purchase> purchases =purchaseRepository.findAll();
        log.info("IN getAllPurchases - purchases: {}",purchases);
        return purchases;
    }

    public Set<Integer> getAllDistinctMonth(){
        Set<Integer> months = purchaseRepository.findAllMonth();
        log.info("Founded month's : {}",months);
        return months;
    }

    public Map<Long,List<String>> getPurchaseBuyerAndShopInfo(){
        List<Purchase> purchases = purchaseRepository.findAll();
        Map<Long,List<String>> result= new HashMap<>();
        purchases.forEach(purchase -> {
            String name = buyerRepository.findById(purchase.getBuyer_id()).orElse(null).getLastname();
            String shop = shopRepository.findById(purchase.getShop_id()).orElse(null).getName();
            List<String> res = new ArrayList<>();
            res.add(name);
            res.add(shop);
            result.put(purchase.getId(),res);
        });
        return result;
    }

    public Map<Long,List<Object>> getPurchaseDetailedInfo(){
        List<Purchase> purchases = purchaseRepository.findAll();
        Map<Long,List<Object>> result= new HashMap<>();
        purchases.forEach(purchase -> {
            Buyer buyer = buyerRepository.findById(purchase.getBuyer_id()).orElse(null);
            Book book = bookRepository.findById(purchase.getBook_id()).orElse(null);
            List<Object> res = new ArrayList<>();
            res.add(purchase.getDate());
            res.add(buyer.getLastname());
            res.add(buyer.getDiscount());
            res.add(book.getName());
            res.add(purchase.getQuantity());
            result.put(purchase.getId(),res);
        });
        return result;
    }

    public Map<Long,List<Object>> getAllPurchasesWithSumGreaterThan(Double sum){
        List<Purchase> purchases = purchaseRepository.findAllBySumGreaterThan(sum);
        Map<Long,List<Object>> result =new HashMap<>();
        purchases.forEach(purchase -> {
            List<Object> res = new ArrayList<>();
            res.add(buyerRepository.findById(purchase.getBuyer_id()).orElse(null).getLastname());
            res.add(purchase.getDate());
            result.put(purchase.getId(),res);
        });
        return result;
    }

    public List<Object[]> findAllPurchasesAfterMarch(Long month){
        return purchaseRepository.findAllPurchasesAfterMonthAndSameLocs(month);
    }
}


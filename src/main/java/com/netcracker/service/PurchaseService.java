package com.netcracker.service;


import com.netcracker.dto.PurchaseDTO;
import com.netcracker.model.Purchase;
import com.netcracker.repos.BookRepository;
import com.netcracker.repos.BuyerRepository;
import com.netcracker.repos.PurchaseRepository;
import com.netcracker.repos.ShopRepository;
import com.netcracker.view.purchase.PurchaseViewDetailedInfo;
import com.netcracker.view.purchase.PurchaseViewGreaterSum;
import com.netcracker.view.purchase.PurchaseViewNameLocDate;
import com.netcracker.view.purchase.PurchaseViewNamesAndShops;
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
        if (purchaseDTO.getBook_id()!=null) purchase.setBook(bookRepository.findById(purchaseDTO.getBook_id()).get());
        if (purchaseDTO.getBuyer_id()!=null) purchase.setBuyer(buyerRepository.findById(purchaseDTO.getBuyer_id()).get());
        if (purchaseDTO.getDate()!=null) purchase.setDate(purchaseDTO.getDate());
        if (purchaseDTO.getQuantity()!=null) purchase.setQuantity(purchaseDTO.getQuantity());
        if (purchaseDTO.getShop_id()!=null) purchase.setShop(shopRepository.findById(purchaseDTO.getShop_id()).get());
        if (purchaseDTO.getSum()!=null) purchase.setSum(purchaseDTO.getSum());
    }
    public void change(Purchase purchaseOld,Purchase purchaseNew){
        purchaseOld.setSum(purchaseNew.getSum());
        purchaseOld.setShop(purchaseNew.getShop());
        purchaseOld.setQuantity(purchaseNew.getQuantity());
        purchaseOld.setDate(purchaseNew.getDate());
        purchaseOld.setBuyer(purchaseNew.getBuyer());
        purchaseOld.setBook(purchaseNew.getBook());
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

    public List<PurchaseViewNamesAndShops>  getPurchaseBuyerAndShopInfo(){
        return purchaseRepository.findAllNamesAndShops();
    }

    public List<PurchaseViewDetailedInfo> getPurchaseDetailedInfo(){
        return purchaseRepository.getDetailedPurchaseInfo();
    }

    public List<PurchaseViewGreaterSum> getAllPurchasesWithSumGreaterThan(Double sum){
        return purchaseRepository.findAllBySumGreaterThan(sum);
    }

    public List<PurchaseViewNameLocDate> findAllPurchasesAfterMarch(Long month){
        return purchaseRepository.findAllPurchasesAfterMonthAndSameLocs(month);
    }
}


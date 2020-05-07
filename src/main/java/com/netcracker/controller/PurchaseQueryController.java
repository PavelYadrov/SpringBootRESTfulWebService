package com.netcracker.controller;

import com.netcracker.service.BookService;
import com.netcracker.service.BuyerService;
import com.netcracker.service.PurchaseService;
import com.netcracker.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/query/")
public class PurchaseQueryController {

    private PurchaseService purchaseService;

    @Autowired
    public PurchaseQueryController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    //Fourth Exercise
    @GetMapping(value = "purchaseInfo")
    public ResponseEntity<Map<Long,List<String>>> getPurchaseBuyerAndShopInfo(){
        return ResponseEntity.ok(purchaseService.getPurchaseBuyerAndShopInfo());
    }

    @GetMapping(value = "purchaseDetailedInfo")
    public ResponseEntity<Map<Long,List<Object>>> getAlmostAllInfoAboutPurchase(){
        return ResponseEntity.ok(purchaseService.getPurchaseDetailedInfo());
    }

}

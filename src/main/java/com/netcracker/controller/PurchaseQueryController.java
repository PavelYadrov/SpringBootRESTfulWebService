package com.netcracker.controller;


import com.netcracker.service.PurchaseService;

import com.netcracker.view.purchase.PurchaseViewDetailedInfo;
import com.netcracker.view.purchase.PurchaseViewNamesAndShops;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "query/")
public class PurchaseQueryController {

    private PurchaseService purchaseService;

    @Autowired
    public PurchaseQueryController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    //Fourth Exercise
    @GetMapping(value = "purchaseInfo")
    public ResponseEntity<List<PurchaseViewNamesAndShops> > getPurchaseBuyerAndShopInfo(){
        return ResponseEntity.ok(purchaseService.getPurchaseBuyerAndShopInfo());
    }

    @GetMapping(value = "purchaseDetailedInfo")
    public ResponseEntity<List<PurchaseViewDetailedInfo>> getAlmostAllInfoAboutPurchase(){
        return ResponseEntity.ok(purchaseService.getPurchaseDetailedInfo());
    }

}

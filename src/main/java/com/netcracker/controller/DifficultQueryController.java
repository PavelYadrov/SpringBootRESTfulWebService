package com.netcracker.controller;

import com.netcracker.dto.BookDTO;
import com.netcracker.model.Shop;
import com.netcracker.service.BookService;
import com.netcracker.service.BuyerService;
import com.netcracker.service.PurchaseService;
import com.netcracker.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/query/dif/")
public class DifficultQueryController {

    private BookService bookService;
    private BuyerService buyerService;
    private PurchaseService purchaseService;
    private ShopService shopService;

    @Autowired
    public DifficultQueryController(BookService bookService,BuyerService buyerService,
                                 PurchaseService purchaseService, ShopService shopService) {
        this.bookService=bookService;
        this.buyerService=buyerService;
        this.purchaseService=purchaseService;
        this.shopService=shopService;
    }

    //Enter the amount you want
    @GetMapping(value = "sum/{sum}")
    public ResponseEntity<Map<Long,List<Object>>> getAllPurchaseInfoGreaterThanSum(@PathVariable(name = "sum") Double sum){
        return ResponseEntity.ok(purchaseService.getAllPurchasesWithSumGreaterThan(sum));
    }
    //Enter the month after which you want to find purchases
    @GetMapping(value = "purchasesAfterMonth/{month}")
    public ResponseEntity<List<Object[]>> findAllPurchasesAfterMounthAndSameLocs(@PathVariable(name = "month") Long month){
        return ResponseEntity.ok(purchaseService.findAllPurchasesAfterMarch(month));
    }

    @GetMapping(value = "shops/{location}/{lowerBind}/{higherBind}")
    public ResponseEntity<List<Shop>> getAllShopsOmitMonthWithDiscountInterval(@PathVariable(name = "location") String loc,
                                                                               @PathVariable(name = "lowerBind",required = false) Integer lower,
                                                                               @PathVariable(name = "higherBind",required = false) Integer higher){
        if((lower<0 || higher<0 )||(lower>higher)||(lower>100||higher>100)){
            return new ResponseEntity("Try again with valid data", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(shopService.getAllShopsOmitLocationWithDiscountInterval(loc,lower,higher));

    }

    @GetMapping(value = "books/{quantity}")
    public ResponseEntity<List<Object[]>> getAllBooksByQuantityBuyPlaced(@PathVariable(name = "quantity")Integer quantity){
        if(quantity<=0){
            return new ResponseEntity("Quantity cannot be negative or 0",HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(bookService.getDifficultBooksInfo(quantity));
    }

}

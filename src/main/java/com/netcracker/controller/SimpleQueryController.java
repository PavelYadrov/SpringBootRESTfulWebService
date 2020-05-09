package com.netcracker.controller;

import com.netcracker.service.BookService;
import com.netcracker.service.BuyerService;
import com.netcracker.service.PurchaseService;
import com.netcracker.service.ShopService;
import com.netcracker.view.book.BookView;
import com.netcracker.view.book.BookViewWithNamesAndPrices;
import com.netcracker.view.buyer.BuyerViewV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "query/")
public class SimpleQueryController {

    private BookService bookService;
    private BuyerService buyerService;
    private PurchaseService purchaseService;
    private ShopService shopService;

    @Autowired
    public SimpleQueryController(BookService bookService,BuyerService buyerService,
                                 PurchaseService purchaseService, ShopService shopService) {
        this.bookService=bookService;
        this.buyerService=buyerService;
        this.purchaseService=purchaseService;
        this.shopService=shopService;
    }

    //Second Exercise

    @GetMapping(value = "byNameAndPrice")
    public ResponseEntity<List<BookView>> getAllDistBooks(){
       return ResponseEntity.ok(bookService.getAllDistinctBooks());
    }

    @GetMapping(value = "distDistricts")
    public ResponseEntity<Set<String>> getAllDistinctDistricts(){
        return ResponseEntity.ok(buyerService.getAllDistricts());
    }

    @GetMapping(value = "distMonths")
    public ResponseEntity<Set<Integer>> getAllDistinctMonths(){
        return ResponseEntity.ok(purchaseService.getAllDistinctMonth());
    }

    //Third Exercise
    @GetMapping(value = "buyersNameAndDisc")
    public ResponseEntity<List<BuyerViewV1>> getAllLastNamesAndDiscByDistrict(){
        return ResponseEntity.ok(buyerService.getAllNamesAndDiscounts());
    }

    @GetMapping(value = "shopNamesWithinTwoLocs/{firstLocation}/{secondLocation}")
    public ResponseEntity<List<String>> getAllShopNamesByLocs(@PathVariable(name = "firstLocation") String firstLoc,
                                                              @PathVariable(name = "secondLocation") String secondLoc){
        List<String> result =shopService.findShopNamesWithinTwoLocations(firstLoc,secondLoc);
        if (result.size()==0) return new ResponseEntity(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(result);
    }
    @GetMapping(value = "bookNamesAndPrices/{minPrice}")
    public ResponseEntity<List<BookViewWithNamesAndPrices>> getAllBookNamesAndPricesByMinPriceAndWord(@PathVariable(name = "minPrice") Double price){
        if(price<=0){
            return new ResponseEntity("Book price cannot be negative or 0", HttpStatus.BAD_REQUEST);
        }
        List<BookViewWithNamesAndPrices> result= bookService.getBooksNamesAndPrices(price);
        if (result.size()==0) return new ResponseEntity(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(result);
    }

}

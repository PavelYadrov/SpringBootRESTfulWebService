package com.netcracker.controller.crud;

import com.netcracker.dto.PurchaseDTO;
import com.netcracker.model.Purchase;
import com.netcracker.repos.PurchaseRepository;
import com.netcracker.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/purchase/")
public class PurchaseController {

    private PurchaseService purchaseService;
    private PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, PurchaseRepository purchaseRepository) {
        this.purchaseService = purchaseService;
        this.purchaseRepository = purchaseRepository;
    }

    @PatchMapping(value = "patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> patchPurchase(@PathVariable(value = "id", required = true) Long id,
                                            @RequestBody PurchaseDTO updates) {

        Purchase purchase = purchaseService.findById(id);
        if (purchase == null) {
            return new ResponseEntity<>("Purchase not found", HttpStatus.BAD_REQUEST);
        } else {
            purchaseService.update(updates, purchase);
            purchaseRepository.save(purchase);
            return ResponseEntity.ok("recourse updated");
        }
    }

    @PostMapping(value = "add")
    public ResponseEntity<String> addPurchase(@RequestBody Purchase purchase) {
        if (purchaseRepository.findById(purchase.getId()).isPresent()) {
            return new ResponseEntity("Purchase with id: " + purchase.getId() + " already registered", HttpStatus.BAD_REQUEST);
        }
        purchaseRepository.save(purchase);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> patchPurchase(@PathVariable(value = "id", required = true) Long id) {

        if (purchaseService.findById(id) == null)
            return new ResponseEntity("Purchase does not exist", HttpStatus.BAD_REQUEST);
        else {
            purchaseService.delete(purchaseService.findById(id));
            return ResponseEntity.ok("Purchase successfully deleted");
        }
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<List<Purchase>> getAll() {
        return ResponseEntity.ok(purchaseService.getAllPurchases());
    }

    @GetMapping(value = "getById/{id}")
    public ResponseEntity<Purchase> getAll(@PathVariable(value = "id", required = true) Long id) {

        if (purchaseService.findById(id) == null)
            return new ResponseEntity("Purchase does not exist", HttpStatus.BAD_REQUEST);
        else {
            return ResponseEntity.ok(purchaseService.findById(id));
        }
    }

    @PutMapping(value = "rewrite/{id}")
    public ResponseEntity<String> rewriteEntity(@PathVariable(value = "id", required = true) Long id,
                                                @RequestBody Purchase purchaseNew) {
        Purchase purchaseOld = purchaseService.findById(id);
        if (purchaseOld == null)
            return new ResponseEntity("Purchase does not exist", HttpStatus.BAD_REQUEST);
        purchaseService.change(purchaseOld, purchaseNew);
        purchaseRepository.save(purchaseOld);
        return ResponseEntity.ok("Data successfully changed");
    }
}

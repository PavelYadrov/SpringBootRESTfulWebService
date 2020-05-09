package com.netcracker.controller.crud;

import com.netcracker.dto.BuyerDTO;
import com.netcracker.model.Buyer;
import com.netcracker.repos.BuyerRepository;
import com.netcracker.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "buyer/")
public class BuyerController {

    private BuyerService buyerService;
    private BuyerRepository buyerRepository;

    @Autowired
    public BuyerController(BuyerService buyerService, BuyerRepository buyerRepository) {
        this.buyerService = buyerService;
        this.buyerRepository = buyerRepository;
    }

    @PatchMapping(value = "patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> patchBuyer(@PathVariable(value = "id", required = true) Long id,
                                             @RequestBody BuyerDTO updates) {

        Buyer buyer = buyerService.findById(id);
        if (buyer == null) {
            return new ResponseEntity<>("Buyer not found", HttpStatus.BAD_REQUEST);
        } else {
            buyerService.update(updates, buyer);
            buyerRepository.save(buyer);
            return ResponseEntity.ok("recourse updated");
        }
    }

    @PostMapping(value = "add")
    public ResponseEntity<String> addBuyer(@RequestBody Buyer buyer) {
        if (buyerRepository.findByLastname(buyer.getLastname()) != null) {
            return new ResponseEntity("Buyer " + buyer.getLastname() + " already registered", HttpStatus.BAD_REQUEST);
        }
        buyerRepository.save(buyer);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> patchBuyer(@PathVariable(value = "id", required = true) Long id) {

        if (buyerService.findById(id) == null)
            return new ResponseEntity("Buyer does not exist", HttpStatus.BAD_REQUEST);
        else {
            buyerService.delete(buyerService.findById(id));
            return ResponseEntity.ok("Buyer successfully deleted");
        }
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<List<Buyer>> getAll() {
        return ResponseEntity.ok(buyerService.getAllBuyers());
    }

    @GetMapping(value = "getById/{id}")
    public ResponseEntity<Buyer> getAll(@PathVariable(value = "id", required = true) Long id) {

        if (buyerService.findById(id) == null)
            return new ResponseEntity("Buyer does not exist", HttpStatus.BAD_REQUEST);
        else {
            return ResponseEntity.ok(buyerService.findById(id));
        }
    }

    @PutMapping(value = "rewrite/{id}")
    public ResponseEntity<String> rewriteEntity(@PathVariable(value = "id", required = true) Long id,
                                                @RequestBody Buyer buyerNew) {
        Buyer buyerOld = buyerService.findById(id);
        if (buyerOld == null)
            return new ResponseEntity("Buyer does not exist", HttpStatus.BAD_REQUEST);
        buyerService.change(buyerOld, buyerNew);
        buyerRepository.save(buyerOld);
        return ResponseEntity.ok("Data successfully changed");
    }
}

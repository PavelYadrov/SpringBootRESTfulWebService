package com.netcracker.controller.crud;

import com.netcracker.dto.ShopDTO;
import com.netcracker.model.Shop;
import com.netcracker.repos.ShopRepository;
import com.netcracker.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "shop/")
public class ShopController {

    private ShopService shopService;
    private ShopRepository shopRepository;

    @Autowired
    public ShopController(ShopService shopService, ShopRepository shopRepository) {
        this.shopService = shopService;
        this.shopRepository = shopRepository;
    }

    @PatchMapping(value = "patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> patchShop(@PathVariable(value = "id", required = true) Long id,
                                             @RequestBody ShopDTO updates) {

        Shop shop = shopService.findById(id);
        if (shop == null) {
            return new ResponseEntity<>("Shop not found", HttpStatus.BAD_REQUEST);
        } else {
            shopService.update(updates, shop);
            shopRepository.save(shop);
            return ResponseEntity.ok("recourse updated");
        }
    }

    @PostMapping(value = "add")
    public ResponseEntity<String> addShop(@RequestBody Shop shop) {
        if (shopRepository.findByName(shop.getName()) != null) {
            return new ResponseEntity("Shop " + shop.getName() + " already registered", HttpStatus.BAD_REQUEST);
        }
        shopRepository.save(shop);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> patchShop(@PathVariable(value = "id", required = true) Long id) {

        if (shopService.findById(id) == null)
            return new ResponseEntity("Shop does not exist", HttpStatus.BAD_REQUEST);
        else {
            shopService.delete(shopService.findById(id));
            return ResponseEntity.ok("Shop successfully deleted");
        }
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<List<Shop>> getAll() {
        return ResponseEntity.ok(shopService.getAllShops());
    }

    @GetMapping(value = "getById/{id}")
    public ResponseEntity<Shop> getAll(@PathVariable(value = "id", required = true) Long id) {

        if (shopService.findById(id) == null)
            return new ResponseEntity("Shop does not exist", HttpStatus.BAD_REQUEST);
        else {
            return ResponseEntity.ok(shopService.findById(id));
        }
    }

    @PutMapping(value = "rewrite/{id}")
    public ResponseEntity<String> rewriteEntity(@PathVariable(value = "id", required = true) Long id,
                                                @RequestBody Shop shopNew) {
        Shop shopOld = shopService.findById(id);
        if (shopOld == null)
            return new ResponseEntity("Shop does not exist", HttpStatus.BAD_REQUEST);
        shopService.change(shopOld, shopNew);
        shopRepository.save(shopOld);
        return ResponseEntity.ok("Data successfully changed");
    }
}

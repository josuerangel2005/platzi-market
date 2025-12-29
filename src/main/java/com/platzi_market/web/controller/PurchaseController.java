package com.platzi_market.web.controller;

import com.platzi_market.domain.DTO.Purchase;
import com.platzi_market.domain.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compra")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll() {
        return new ResponseEntity<List<Purchase>>(this.purchaseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/com/{id}")
    public ResponseEntity<List<Purchase>> getByClient(@PathVariable String clienteId) {
        return this.purchaseService.getByClient(clienteId).map(purchases -> new ResponseEntity<List<Purchase>>(purchases, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<Purchase>(this.purchaseService.save(purchase), HttpStatus.OK);
    }


}

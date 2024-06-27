package com.example.fx_deals.Controller;

import com.example.fx_deals.Entity.FxDeal;
import com.example.fx_deals.Exceptions.DuplicateDealException;
import com.example.fx_deals.Service.FxDealService;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/fx_deals")
public class FxDealController {

    @Autowired
    private FxDealService fxDealService;

    /*@PostMapping
    public ResponseEntity<?> createDeal(@Validated @RequestBody FxDeal fxDeal) {
        log.info("Attempting to create a new deal with unique ID: {}", fxDeal.getDealUniqueId());
        if (fxDealService.existsByDealUniqueId(fxDeal.getDealUniqueId())) {
            log.warn("Deal creation failed: Deal with unique ID {} already exists.", fxDeal.getDealUniqueId());
            return ResponseEntity.badRequest().body("Deal with this unique ID already exists.");
        }
        FxDeal savedDeal;
        try {
            savedDeal = fxDealService.saveDeal(fxDeal);
            
        } catch (DuplicateDealException e) {
            log.error("Deal creation failed due to an exception: ", e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A duplicate deal was encountered.");
        }
        if (savedDeal != null) {
            log.info("Deal successfully created with unique ID: {}", savedDeal.getDealUniqueId());
            return ResponseEntity.ok(savedDeal);
        } else {
            // Handle the case where savedDeal is null (though this should be rare if exceptions are handled correctly)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the deal.");
        }
    }*/

    // i changed to this method so that we can recieve multiple deals in the same request in the form of List,
    // and also this method will return a list of saved deals and a list of failed deals
    //also this method post each deal alone in the background using the saveDeal method in the service class
    // this leads to no rollback happening if one of the deals failed to be saved
    @PostMapping
    public ResponseEntity<?> createDeals(@Validated @RequestBody List<FxDeal> fxDeals) {
    List<FxDeal> savedDeals = new ArrayList<>();
    List<String> failedDealIds = new ArrayList<>();

    for (FxDeal fxDeal : fxDeals) {
        log.info("Attempting to create a new deal with unique ID: {}", fxDeal.getDealUniqueId());
        if (fxDealService.existsByDealUniqueId(fxDeal.getDealUniqueId())) {
            log.warn("Deal creation failed: Deal with unique ID {} already exists.", fxDeal.getDealUniqueId());
            failedDealIds.add(fxDeal.getDealUniqueId());
            continue;
        }
        try {
            FxDeal savedDeal = fxDealService.saveDeal(fxDeal);
            if (savedDeal != null) {
                log.info("Deal successfully created with unique ID: {}", savedDeal.getDealUniqueId());
                savedDeals.add(savedDeal);
            } else {
                // Handle the case where savedDeal is null (though this should be rare if exceptions are handled correctly)
                log.error("An error occurred while saving the deal with unique ID: {}", fxDeal.getDealUniqueId());
                failedDealIds.add(fxDeal.getDealUniqueId());
            }
        } catch (DuplicateDealException e) {
            log.error("Deal creation failed due to an exception for unique ID {}: ", fxDeal.getDealUniqueId(), e);
            failedDealIds.add(fxDeal.getDealUniqueId());
        }
    }

    if (!failedDealIds.isEmpty()) {
        log.info("Some deals failed to be saved: {}", failedDealIds);
        log.info("Showing Deals that are saved and Deals that failed to be saved.");
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(Map.of("savedDeals", savedDeals, "failedDealIds", failedDealIds));
    }
    return ResponseEntity.ok(savedDeals);
}

    @GetMapping
    public ResponseEntity<?> getAllDeals() {
        log.info("Retrieving all deals.");
        return fxDealService.getAllDeals();
    }

   /* @GetMapping("/api/fx_deals/{dealUniqueId}")
    public ResponseEntity<?> getDealByUniqueId(@PathVariable String dealUniqueId) {
        log.info("Retrieving deal with unique ID : {}", dealUniqueId);
        return fxDealService.getDealByUniqueId(dealUniqueId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    @GetMapping("api/fx_deals/{dealUniqueId}")
    public ResponseEntity<?> getDealByUniqueId(@PathVariable String dealUniqueId) {
        log.info("Retrieving deal with unique ID : {}", dealUniqueId);
        return fxDealService.getDealByUniqueId(dealUniqueId);
                /*.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());*/
    }

    /*@GetMapping("api/fx_deals/{fromCurrency}")
    public ResponseEntity<?> getDealByFromCurrency(@PathVariable String fromCurrency) {
        //for testing System.out.println("fromCurrency: " + fromCurrency);
        log.info("Retrieving deal(s) with fromCurrency : {}", fromCurrency);
        return fxDealService.getDealByFromCurrency(fromCurrency)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    /*@GetMapping("api/fx_deals/{dealUniqueId}")
    public ResponseEntity<FxDeal> getFxDealById(@PathVariable String dealUniqueId) {
        Optional<FxDeal> fxDeal = fxDealRepository.findById(dealUniqueId);
        if (fxDeal.isPresent()) {
            return ResponseEntity.ok(fxDeal.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    
}

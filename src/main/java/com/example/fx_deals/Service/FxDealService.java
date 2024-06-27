package com.example.fx_deals.Service;

import com.example.fx_deals.Entity.FxDeal;
import com.example.fx_deals.Exceptions.DuplicateDealException;
import com.example.fx_deals.Repository.FxDealRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class FxDealService {

    @Autowired
    private FxDealRepository fxDealRepository;

    public FxDeal saveDeal(FxDeal fxDeal) throws DuplicateDealException{
        if (fxDealRepository.existsById(fxDeal.getDealUniqueId())) {
            throw new DuplicateDealException("Duplicate deal ID: " + fxDeal.getDealUniqueId());
        }
        return fxDealRepository.save(fxDeal);
    }

    /*public List<FxDeal> getAllDeals() {
        if (fxDealRepository.findAll().isEmpty()) {
            log.info("No deals available.");

            return (List<FxDeal>) ResponseEntity.badRequest().body("Deal with this unique ID already exists.");
        }
        return fxDealRepository.findAll();
    }*/

    public ResponseEntity<?> getAllDeals() {
    List<FxDeal> deals = fxDealRepository.findAll();
    if (deals.isEmpty()) {
        log.info("No deals available.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No deals available.");
    }
    return ResponseEntity.ok(deals);
}

    /*public Optional<FxDeal> getDealByUniqueId(String dealUniqueId) {
        return fxDealRepository.findByDealUniqueId(dealUniqueId);
    }*/

    public ResponseEntity<?> getDealByUniqueId(String dealUniqueId) {
        Optional<FxDeal> deal = fxDealRepository.findByDealUniqueId(dealUniqueId);
        if (!deal.isPresent()) {
            log.info("Deal with unique ID " + dealUniqueId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deal with unique ID " + dealUniqueId + " not found.");
        }
        return ResponseEntity.ok(deal.get());
    }

    public Optional<FxDeal> getDealByFromCurrency(String fromCurrency) {
        return fxDealRepository.findByfromCurrencyIsoCode(fromCurrency);
    }

    public boolean existsByDealUniqueId(String dealUniqueId) {
        return fxDealRepository.findByDealUniqueId(dealUniqueId).isPresent();
    }
}

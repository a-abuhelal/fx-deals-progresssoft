package com.example.fx_deals;

import com.example.fx_deals.Controller.FxDealController;
import com.example.fx_deals.Entity.FxDeal;
import com.example.fx_deals.Exceptions.DuplicateDealException;
import com.example.fx_deals.Service.FxDealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FxDealControllerTest {

    @Mock
    private FxDealService fxDealService;

    @InjectMocks
    private FxDealController fxDealController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDealsSuccess() throws DuplicateDealException {
        FxDeal fxDeal1 = new FxDeal("1", "USD", "EUR", LocalDateTime.now(), 1000.0);
        FxDeal fxDeal2 = new FxDeal("2", "GBP", "USD", LocalDateTime.now(), 2000.0);
        List<FxDeal> fxDeals = Arrays.asList(fxDeal1, fxDeal2);

        when(fxDealService.existsByDealUniqueId(fxDeal1.getDealUniqueId())).thenReturn(false);
        when(fxDealService.existsByDealUniqueId(fxDeal2.getDealUniqueId())).thenReturn(false);
        when(fxDealService.saveDeal(fxDeal1)).thenReturn(fxDeal1);
        when(fxDealService.saveDeal(fxDeal2)).thenReturn(fxDeal2);

        ResponseEntity<?> response = fxDealController.createDeals(fxDeals);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(fxDealService, times(1)).existsByDealUniqueId(fxDeal1.getDealUniqueId());
        verify(fxDealService, times(1)).existsByDealUniqueId(fxDeal2.getDealUniqueId());
        verify(fxDealService, times(1)).saveDeal(fxDeal1);
        verify(fxDealService, times(1)).saveDeal(fxDeal2);
    }

    @Test
    void testCreateDealsPartialSuccess() throws DuplicateDealException {
        FxDeal fxDeal1 = new FxDeal("1", "USD", "EUR", LocalDateTime.now(), 1000.0);
        FxDeal fxDeal2 = new FxDeal("2", "GBP", "USD", LocalDateTime.now(), 2000.0);
        List<FxDeal> fxDeals = Arrays.asList(fxDeal1, fxDeal2);

        when(fxDealService.existsByDealUniqueId(fxDeal1.getDealUniqueId())).thenReturn(true);
        when(fxDealService.existsByDealUniqueId(fxDeal2.getDealUniqueId())).thenReturn(false);
        when(fxDealService.saveDeal(fxDeal2)).thenReturn(fxDeal2);

        ResponseEntity<?> response = fxDealController.createDeals(fxDeals);
        assertEquals(206, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(fxDealService, times(1)).existsByDealUniqueId(fxDeal1.getDealUniqueId());
        verify(fxDealService, times(1)).existsByDealUniqueId(fxDeal2.getDealUniqueId());
        verify(fxDealService, never()).saveDeal(fxDeal1);
        verify(fxDealService, times(1)).saveDeal(fxDeal2);
    }

    /*@Test
    void testGetAllDeals() {
        FxDeal fxDeal = new FxDeal("1", "USD", "EUR", LocalDateTime.now(), 1000.0);

        when(fxDealService.getAllDeals()).thenReturn(ResponseEntity.ok(Collections.singletonList(fxDeal)));

        ResponseEntity<?> response = fxDealController.getAllDeals();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(fxDealService, times(1)).getAllDeals();
    }*/

    

    
}

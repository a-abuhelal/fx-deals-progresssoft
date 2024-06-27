package com.example.fx_deals;

import com.example.fx_deals.Entity.FxDeal;
import com.example.fx_deals.Exceptions.DuplicateDealException;
import com.example.fx_deals.Repository.FxDealRepository;
import com.example.fx_deals.Service.FxDealService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FxDealServiceTest {

    @Mock
    private FxDealRepository fxDealRepository;

    @InjectMocks
    private FxDealService fxDealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDealSuccess() throws DuplicateDealException {
        FxDeal fxDeal = new FxDeal("1", "USD", "EUR", LocalDateTime.now(), 1000.0);

        when(fxDealRepository.existsById(fxDeal.getDealUniqueId())).thenReturn(false);
        when(fxDealRepository.save(fxDeal)).thenReturn(fxDeal);

        FxDeal savedDeal = fxDealService.saveDeal(fxDeal);
        assertNotNull(savedDeal);
        assertEquals(fxDeal.getDealUniqueId(), savedDeal.getDealUniqueId());

        verify(fxDealRepository, times(1)).existsById(fxDeal.getDealUniqueId());
        verify(fxDealRepository, times(1)).save(fxDeal);
    }

    @Test
    void testSaveDealDuplicate() {
        FxDeal fxDeal = new FxDeal("1", "USD", "EUR", LocalDateTime.now(), 1000.0);

        when(fxDealRepository.existsById(fxDeal.getDealUniqueId())).thenReturn(true);

        assertThrows(DuplicateDealException.class, () -> {
            fxDealService.saveDeal(fxDeal);
        });

        verify(fxDealRepository, times(1)).existsById(fxDeal.getDealUniqueId());
        verify(fxDealRepository, never()).save(fxDeal);
    }

    /*@SuppressWarnings("deprecation")
    @Test
    void testGetAllDealsSuccess() {
        FxDeal fxDeal = new FxDeal("1", "USD", "EUR", LocalDateTime.now(), 1000.0);

        when(fxDealRepository.findAll()).thenReturn(Collections.singletonList(fxDeal));

        ResponseEntity<?> response = FxDealService.getAllDeals();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(fxDealRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDealsNotFound() {
        when(fxDealRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = fxDealService.getAllDeals();
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No deals available.", response.getBody());

        verify(fxDealRepository, times(1)).findAll();
    }*/

   
   
}

package com.example.fx_deals.Entity;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fx_deals")
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class FxDeal{

// this might be unnecessary!!!!!!!!
   // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
//end of comment
    @Id
    @Column(name = "deal_id", nullable = false, unique = true)
    private String dealUniqueId;

    @Column(name = "from_currency", nullable = false)
    private String fromCurrencyIsoCode;

    @Column(name = "to_currency", nullable = false)
    private String toCurrencyIsoCode;

    @Column(name = "deal_timestamp", nullable = false)
    private LocalDateTime dealTimestamp;

    @Column(name = "deal_amount", nullable = false)
    private Double dealAmount;


   // public FxDeal() {
    //}

    // Parameterized constructor
    public FxDeal(String dealUniqueId, String fromCurrencyIsoCode, String toCurrencyIsoCode, LocalDateTime dealTimestamp, Double dealAmount) {
        this.dealUniqueId = dealUniqueId;
        this.fromCurrencyIsoCode = fromCurrencyIsoCode;
        this.toCurrencyIsoCode = toCurrencyIsoCode;
        this.dealTimestamp = dealTimestamp;
        this.dealAmount = dealAmount;
    }

    // Getters and Setters
    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public String getDealUniqueId() {
        return dealUniqueId;
    }

    public void setDealUniqueId(String dealUniqueId) {
        this.dealUniqueId = dealUniqueId;
    }

    public String getFromCurrencyIsoCode() {
        return fromCurrencyIsoCode;
    }

    public void setFromCurrencyIsoCode(String fromCurrencyIsoCode) {
        this.fromCurrencyIsoCode = fromCurrencyIsoCode;
    }

    public String getToCurrencyIsoCode() {
        return toCurrencyIsoCode;
    }

    public void setToCurrencyIsoCode(String toCurrencyIsoCode) {
        this.toCurrencyIsoCode = toCurrencyIsoCode;
    }

    public LocalDateTime getDealTimestamp() {
        return dealTimestamp;
    }

    public void setDealTimestamp(LocalDateTime dealTimestamp) {
        this.dealTimestamp = dealTimestamp;
    }

    public Double getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(Double dealAmount) {
        this.dealAmount = dealAmount;
    }
}

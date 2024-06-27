package com.example.fx_deals.Repository;

import com.example.fx_deals.Entity.FxDeal;
//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FxDealRepository extends JpaRepository<FxDeal, String> {
    Optional<FxDeal> findByDealUniqueId(String dealUniqueId);
    Optional<FxDeal> findByfromCurrencyIsoCode(String fromCurrency);
    //@Query(value = "SELECT * FROM FxDeal  WHERE from_currency = :from_curr", nativeQuery = true)
    //Optional<FxDeal> findDeal(String from_curr);

    //@Query(value = "SELECT * FROM fx_deals  WHERE deal_Id = :dealUniqueId",nativeQuery = true)
    //Optional<FxDeal> findByDealUniqueId(String dealUniqueId);
    boolean existsByDealUniqueId(String dealUniqueId);
}

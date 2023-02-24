package com.inditex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import com.inditex.models.Prices;
import com.inditex.models.Brands;
import com.inditex.models.Products;

@Repository
@Transactional
public interface PricesRepository extends JpaRepository<Prices, Integer> {
    List<Prices> findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(LocalDateTime startDate, LocalDateTime endDate, Brands brand, Products product);

}

package com.inditex.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.models.Prices;
import com.inditex.services.PricesService;

@Slf4j
@RestController
@RequestMapping(PricesController.BASE_MAPPING)
@Validated
public class PricesController {

    static final String BASE_MAPPING = "/api/prices";
    static final String PATH_PRICES_FILTER_BY_DATES_BRAND_PRODUCT = "/filterByDatesBrandProduct";
    private PricesService pricesService;

    public PricesController(PricesService pricesService){
        this.pricesService = pricesService;
    }

    @GetMapping(value=PATH_PRICES_FILTER_BY_DATES_BRAND_PRODUCT, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Prices>> getPricesByDatesAndBrandAndProduct(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "brandId") int brandId,
            @RequestParam(value = "productId") int productId){

        log.info("REST filterByDatesBrandProduct startDate {} , endDate {} , brandId {} , productId {}", startDate, endDate, brandId, productId);

        List<Prices> pricesList = pricesService.getPricesBetweenDatesAndBrandAndProduct(startDate, endDate, brandId, productId);

        return ResponseEntity.ok(pricesList);
    }
}

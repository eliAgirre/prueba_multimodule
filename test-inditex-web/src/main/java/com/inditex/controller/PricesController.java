package com.inditex.controller;

import java.util.List;

import com.inditex.exception.ServiceException;
import com.inditex.utils.Constants;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

import com.inditex.models.Prices;
import com.inditex.services.PricesService;

@Slf4j
@RestController
@RequestMapping(Constants.BASE_MAPPING)
@Validated
public class PricesController {
    private PricesService pricesService;

    public PricesController(PricesService pricesService){
        this.pricesService = pricesService;
    }

    @GetMapping(Constants.PATH_PRICES_FILTER_BY_DATES_BRAND_PRODUCT)
    public ResponseEntity<List<Prices>> getPricesByDatesAndBrandAndProduct(
            @NotBlank @RequestParam(value = "startDate") String startDate,
            @NotBlank @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "brandId") int brandId,
            @RequestParam(value = "productId") int productId) throws ServiceException {

        log.info(Constants.LOG_CONTROLLER, startDate, endDate, brandId, productId);

        List<Prices> pricesList = null;

        try {
            pricesList = pricesService.getPricesBetweenDatesAndBrandAndProduct(startDate, endDate, brandId, productId);
        }
        catch (ServiceException e) {
            throw new ServiceException(e.getCode(), e.getHttpStatus(), e.getMessage(), e.getCause(), e.getParams());
        }

        return ResponseEntity.ok(pricesList);
    }
}

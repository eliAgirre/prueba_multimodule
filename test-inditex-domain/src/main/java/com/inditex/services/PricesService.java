package com.inditex.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.inditex.utils.Utility;
import com.inditex.utils.Constants;

import com.inditex.models.Brands;
import com.inditex.models.Prices;
import com.inditex.models.Products;

import com.inditex.exception.ServiceErrorCatalog;
import com.inditex.exception.ServiceException;

import com.inditex.repository.PricesRepository;

@Slf4j
@Service
public class PricesService {

    private Brands brand;
    private Products product;
    private PricesRepository pricesRepository;
    private BrandsServices brandsServices;
    private ProductsService productsService;

    public PricesService(PricesRepository pricesRepository,
                         BrandsServices brandsServices,
                         ProductsService productsService,
                         Brands brand,
                         Products product){

        this.pricesRepository = pricesRepository;
        this.brandsServices = brandsServices;
        this.productsService = productsService;
        this.brand = brand;
        this.product = product;
    }

    @PostConstruct
    public void initPrices(){

        brand = brandsServices.initBrand();
        product = productsService.initProduct();

        pricesRepository.saveAll(Stream.of(new Prices(1, brand,
                                Utility.getLocalDateTimeFromString(Constants.START_DATE_STRING_1, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING_1, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                1, product, 0, 35.50, Constants.CURR),

                                new Prices(2, brand,
                                        Utility.getLocalDateTimeFromString(Constants.START_DATE_STRING_2, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING_2, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        2, product, 1, 25.45, Constants.CURR),

                                new Prices(3, brand,
                                        Utility.getLocalDateTimeFromString(Constants.START_DATE_STRING_3, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING_3, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        3, product, 1, 30.50, Constants.CURR),

                                new Prices(4, brand,
                                        Utility.getLocalDateTimeFromString(Constants.START_DATE_STRING_4, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING_4, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        4, product, 1, 38.95, Constants.CURR)
                                )
                                .collect(Collectors.toList()));
    }

    public List<Prices> getPricesBetweenDatesAndBrandAndProduct(String startDate, String endDate, int brandId, int productId) {

        try {
            log.info("getPricesBetweenDatesAndBrandAndProduct startDate {} endDate {} brandId {} productId {} ",  startDate, endDate, brandId, productId);

            if (Objects.isNull(startDate)) {
                throw new ServiceException.Builder(String.valueOf(ServiceErrorCatalog.START_DATE_IS_NOT_CORRRECT))
                        .withHttpStatus(HttpStatus.BAD_REQUEST).build();
            }
            if (Objects.isNull(endDate)) {
                throw new ServiceException.Builder(String.valueOf(ServiceErrorCatalog.END_DATE_IS_NOT_CORRRECT))
                        .withHttpStatus(HttpStatus.BAD_REQUEST).build();
            }

            if (Objects.isNull(brandId)) {
                throw new ServiceException.Builder(String.valueOf(ServiceErrorCatalog.BRAND_ID_IS_NOT_CORRRECT))
                        .withHttpStatus(HttpStatus.BAD_REQUEST).build();
            }

            if (Objects.isNull(productId)) {
                throw new ServiceException.Builder(String.valueOf(ServiceErrorCatalog.PRODUCT_ID_IS_NOT_CORRRECT))
                        .withHttpStatus(HttpStatus.BAD_REQUEST).build();
            }

            LocalDateTime localStartDate = Utility.getLocalDateTimeFromString(startDate, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);
            LocalDateTime localEndDate = Utility.getLocalDateTimeFromString(endDate, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);

            return pricesRepository.findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(localStartDate, localEndDate, brand, product);

        } catch (Exception e) {
            log.error(String.format(ServiceErrorCatalog.ERROR_OBTAINING_PRICES+" of startDate %s", startDate), e);
            return new ArrayList<>();
        }
    }
}

package com.inditex.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import com.inditex.models.Brands;
import com.inditex.models.Products;
import com.inditex.repository.PricesRepository;
import com.inditex.utils.Constants;
import com.inditex.utils.Utility;

class PricesServiceTest {

    private PricesService mockedPricesService;

    private BrandsServices mockedBrandsServices;

    private ProductsService mockedProductsService;

    private PricesRepository mockedPricesRepository;

    private Brands mockedBrand;

    private Products mockedProduct;

    @BeforeEach
    public void setUp() {

        mockedPricesRepository = mock(PricesRepository.class);
        mockedBrandsServices = mock(BrandsServices.class);
        mockedProductsService = mock(ProductsService.class);
        mockedBrand = mock(Brands.class);
        mockedProduct = mock(Products.class);
        mockedPricesService = new PricesService(mockedPricesRepository, mockedBrandsServices, mockedProductsService, mockedBrand, mockedProduct);
    }

    @Test
    void test1_getPricesAt10ByDate14BrandId1AndProductId35435_should_call_pricesRepository() {
        System.out.println("test1_getPricesAt10ByDate14BrandId1AndProductId35435_should_call_pricesRepository");

        // Given
        LocalDateTime startDate = Utility.getLocalDateTimeFromString(Constants.TEST_1_START_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);
        LocalDateTime endDate = Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);

        // When
        mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(Constants.TEST_1_START_DATE_STRING, Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID);

        // Then
        verify(mockedPricesRepository).findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(startDate, endDate, mockedBrand, mockedProduct);
        System.out.println();
    }

    @Test
    void test2_getPricesAt16ByDate14BrandId1AndProductId35435_should_call_pricesRepository() {
        System.out.println("test2_getPricesAt16ByDate14BrandId1AndProductId35435_should_call_pricesRepository");

        // Given
        LocalDateTime startDate = Utility.getLocalDateTimeFromString(Constants.TEST_2_START_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);
        LocalDateTime endDate = Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);

        // When
        mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(Constants.TEST_2_START_DATE_STRING, Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID);

        // Then
        verify(mockedPricesRepository).findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(startDate, endDate, mockedBrand, mockedProduct);
        System.out.println();
    }

    @Test
    void test3_getPricesAt21ByDate14BrandId1AndProductId35435_should_call_pricesRepository() {
        System.out.println("test3_getPricesAt21ByDate14BrandId1AndProductId35435_should_call_pricesRepository");

        // Given
        LocalDateTime startDate = Utility.getLocalDateTimeFromString(Constants.TEST_3_START_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);
        LocalDateTime endDate = Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);

        // When
        mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(Constants.TEST_3_START_DATE_STRING, Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID);

        // Then
        verify(mockedPricesRepository).findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(startDate, endDate, mockedBrand, mockedProduct);
        System.out.println();
    }

    @Test
    void test4_getPricesAt10ByDate15BrandId1AndProductId35435_should_call_pricesRepository() {
        System.out.println("test4_getPricesAt10ByDate15BrandId1AndProductId35435_should_call_pricesRepository");

        // Given
        LocalDateTime startDate = Utility.getLocalDateTimeFromString(Constants.TEST_4_START_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);
        LocalDateTime endDate = Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);

        // When
        mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(Constants.TEST_4_START_DATE_STRING, Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID);

        // Then
        verify(mockedPricesRepository).findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(startDate, endDate, mockedBrand, mockedProduct);
        System.out.println();
    }

    @Test
    void test5_getPricesAt21ByDate16BrandId1AndProductId35435_should_call_pricesRepository() {
        System.out.println("test5_getPricesAt21ByDate16BrandId1AndProductId35435_should_call_pricesRepository");

        // Given
        LocalDateTime startDate = Utility.getLocalDateTimeFromString(Constants.TEST_5_START_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);
        LocalDateTime endDate = Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);

        // When
        mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(Constants.TEST_5_START_DATE_STRING, Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID);

        // Then
        verify(mockedPricesRepository).findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(startDate, endDate, mockedBrand, mockedProduct);
        System.out.println();
    }
    
}
package com.inditex.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.inditex.JsonToObjectsCreator;
import com.inditex.model.request.PriceRqTest;
import com.inditex.models.Prices;
import com.inditex.services.PricesService;

public class PricesControllerTest {

    @Autowired
    private JsonToObjectsCreator json;

    @Mock
    private PricesService mockedPricesService;

    private PricesController pricesController;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        pricesController = new PricesController(mockedPricesService);
        json = new JsonToObjectsCreator();
    }
    @Test
    void Test1_Given_StartDateAt10Date14AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList() throws Exception {
        System.out.println("Test1_Given_StartDateAt10Date14AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList");
        // Given
        PriceRqTest rq1 = json.test1PriceRqAt10Date14();
        List<Prices> rsList1 = json.test1PriceRsAt10Date14();

        // When
        when(mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(rq1.getStartDate(), rq1.getEndDate(),
                                                            rq1.getBrandId(), rq1.getProductId())).thenReturn(rsList1);

        ResponseEntity<List<Prices>> responseEntity = pricesController.getPricesByDatesAndBrandAndProduct(rq1.getStartDate(),
                                                            rq1.getEndDate(), rq1.getBrandId(), rq1.getProductId());
        responseEntity.getBody().stream()
                .map( price -> price.toString() )
                .forEach(System.out::println);
        System.out.println();

        // Then
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(rsList1.size(), responseEntity.getBody().size());
        assertEquals(rsList1.get(0), responseEntity.getBody().get(0));
        assertEquals(rsList1.get(1), responseEntity.getBody().get(1));
        assertEquals(rsList1.get(2), responseEntity.getBody().get(2));
        assertEquals(rsList1.get(3), responseEntity.getBody().get(3));
    }
    @Test
    void Test2_Given_StartDateAt16Date14AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList() throws Exception {
        System.out.println("Test2_Given_StartDateAt16Date14AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList");
        // Given
        PriceRqTest rq2 = json.test2PriceRqAt16Date14();
        List<Prices> rsList2 = json.test2PriceRsAt16Date14();

        // When
        when(mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(rq2.getStartDate(), rq2.getEndDate(),
                rq2.getBrandId(), rq2.getProductId())).thenReturn(rsList2);

        ResponseEntity<List<Prices>> responseEntity = pricesController.getPricesByDatesAndBrandAndProduct(rq2.getStartDate(),
                rq2.getEndDate(), rq2.getBrandId(), rq2.getProductId());
        responseEntity.getBody().stream()
                .map( price -> price.toString() )
                .forEach(System.out::println);
        System.out.println();

        // Then
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(rsList2.size(), responseEntity.getBody().size());
        assertEquals(rsList2.get(0), responseEntity.getBody().get(0));
        assertEquals(rsList2.get(1), responseEntity.getBody().get(1));
    }
    @Test
    void Test3_Given_StartDateAt21Date14AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList() throws Exception {
        System.out.println("Test3_Given_StartDateAt21Date14AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList");
        // Given
        PriceRqTest rq3 = json.test3PriceRqAt21Date14();
        List<Prices> rsList3 = json.test3PriceRsAt21Date14();

        // When
        when(mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(rq3.getStartDate(), rq3.getEndDate(),
                rq3.getBrandId(), rq3.getProductId())).thenReturn(rsList3);

        ResponseEntity<List<Prices>> responseEntity = pricesController.getPricesByDatesAndBrandAndProduct(rq3.getStartDate(),
                rq3.getEndDate(), rq3.getBrandId(), rq3.getProductId());
        responseEntity.getBody().stream()
                .map( price -> price.toString() )
                .forEach(System.out::println);
        System.out.println();

        // Then
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(rsList3.size(), responseEntity.getBody().size());
        assertEquals(rsList3.get(0), responseEntity.getBody().get(0));
        assertEquals(rsList3.get(1), responseEntity.getBody().get(1));
    }
    @Test
    void Test4_Given_StartDateAt10Date15AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList() throws Exception {
        System.out.println("Test4_Given_StartDateAt10Date15AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList");
        // Given
        PriceRqTest rq4 = json.test4PriceRqAt10Date15();
        List<Prices> rsList4 = json.test4PriceRsAt10Date15();

        // When
        when(mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(rq4.getStartDate(), rq4.getEndDate(),
                rq4.getBrandId(), rq4.getProductId())).thenReturn(rsList4);

        ResponseEntity<List<Prices>> responseEntity = pricesController.getPricesByDatesAndBrandAndProduct(rq4.getStartDate(),
                rq4.getEndDate(), rq4.getBrandId(), rq4.getProductId());
        responseEntity.getBody().stream()
                .map( price -> price.toString() )
                .forEach(System.out::println);
        System.out.println();

        // Then
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(rsList4.size(), responseEntity.getBody().size());
        assertEquals(rsList4.get(0), responseEntity.getBody().get(0));
    }

    @Test
    void Test5_Given_StartDateAt21Date16AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList() throws Exception {
        System.out.println("Test5_Given_StartDateAt21Date16AndEndDateAndBrandAndProduct_When_getPriceProduct_Then_returns_PricesList");
        // Given
        PriceRqTest rq5 = json.test5PriceRqAt21Date16();
        List<Prices> rsList5 = json.test5PriceRsAt21Date16();

        // When
        when(mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(rq5.getStartDate(), rq5.getEndDate(),
                rq5.getBrandId(), rq5.getProductId())).thenReturn(rsList5);

        ResponseEntity<List<Prices>> responseEntity = pricesController.getPricesByDatesAndBrandAndProduct(rq5.getStartDate(),
                rq5.getEndDate(), rq5.getBrandId(), rq5.getProductId());
        responseEntity.getBody().stream()
                .map(price -> price.toString())
                .forEach(System.out::println);
        System.out.println();

        // Then
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(rsList5.size(), responseEntity.getBody().size());
    }
}

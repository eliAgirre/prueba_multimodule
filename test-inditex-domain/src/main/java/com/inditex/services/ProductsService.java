package com.inditex.services;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.inditex.utils.Constants;
import com.inditex.models.Products;
import com.inditex.repository.ProductsRepository;

@Slf4j
@Service
public class ProductsService {

    private Products product;

    private ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository, Products product){
        this.productsRepository = productsRepository;
        this.product = product;
    }

    @PostConstruct
    public Products initProduct(){
        product = generateProduct();
        return productsRepository.save(product);
    }

    private Products generateProduct(){
        product.setProducId(Constants.PRODUCT_ID);
        product.setProductName(Constants.PRODUCT_NAME);
        product.setProductDescripion(Constants.EMPTY);
        return product;
    }
}

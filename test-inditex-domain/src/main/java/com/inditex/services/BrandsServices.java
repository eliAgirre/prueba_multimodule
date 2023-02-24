package com.inditex.services;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.inditex.utils.Constants;
import com.inditex.models.Brands;
import com.inditex.repository.BrandsRepository;

@Slf4j
@Service
public class BrandsServices {
    private Brands brand;

    private BrandsRepository brandsRepository;

    public BrandsServices(BrandsRepository brandsRepository, Brands brand){
        this.brandsRepository = brandsRepository;
        this.brand = brand;
    }

    @PostConstruct
    public Brands initBrand(){
        brand = generateBrand();
        return brandsRepository.save(brand);
    }

    private Brands generateBrand(){
        brand.setBrandId(Constants.BRAND_ID);
        brand.setBrandDescription(Constants.BRAND_DESCRIPTION);
        return brand;
    }
}

package com.inditex.repository;

import com.inditex.models.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandsRepository extends JpaRepository<Brands, Integer> {
}

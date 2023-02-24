package com.inditex.models;

import org.springframework.stereotype.Component;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Component
@ToString
public class Brands implements Serializable {

    private static final long serialVersionUID = 7476566482863833562L;
    @Id
    private int brandId;
    private String brandDescription;
}


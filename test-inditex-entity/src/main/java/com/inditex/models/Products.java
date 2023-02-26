package com.inditex.models;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Component
@Entity
public class Products implements Serializable {

    private static final long serialVersionUID = -723420804624938234L;

    @Id
    private int producId;
    private String productName;
    private String productDescripion;

}


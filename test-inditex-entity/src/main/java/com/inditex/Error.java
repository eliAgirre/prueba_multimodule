package com.inditex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Getter
public class Error implements Serializable {
    private String code;
    private String message;
    private List<String> errors;
}

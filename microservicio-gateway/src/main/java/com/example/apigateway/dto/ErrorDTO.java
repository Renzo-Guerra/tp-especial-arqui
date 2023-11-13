package com.example.apigateway.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public final class ErrorDTO {

    private final String message;
   // private final LocalDate date = LocalDate.now();
}

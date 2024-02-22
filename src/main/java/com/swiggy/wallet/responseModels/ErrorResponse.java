package com.swiggy.wallet.responseModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String message;
    private int status;
}

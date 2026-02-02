package com.coders.hospitalmanagement.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BillRequestDTO {

    @NotNull
    @Positive
    private Double amount;

    // getter & setter
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}


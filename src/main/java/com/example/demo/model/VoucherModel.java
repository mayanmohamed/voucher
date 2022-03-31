package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class VoucherModel {


    Long id;

    @NotNull(message = "the serial number cannot be null")
    String serialNumber;
    Date createdAt;

    @Min(0)
    @NotNull(message = "the amount cannot be null")
    Double amount;

    @Min(0)
    @NotNull(message = "the percenatge cannot be null")
    Integer percentage;

    byte[] requestId;
}

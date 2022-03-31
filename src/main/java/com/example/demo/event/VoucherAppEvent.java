package com.example.demo.event;

import com.example.demo.exception.Exceptions;
import com.example.demo.exception.VoucherException;
import com.example.demo.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.http.ResponseEntity;

public class VoucherAppEvent extends ApplicationEvent {

    @Autowired
    VoucherService voucherService;

    @Autowired
    Exceptions exceptions;

    public VoucherAppEvent(Object source , VoucherService voucherService) {
        super(source);
        this.voucherService = voucherService;
    }

    public ResponseEntity<Object> getAllVouchers(){
        try {
            return voucherService.getAllVouchers();
        } catch (VoucherException e) {
            return exceptions.handleVoucherNotFound(e);
        }
    }
}

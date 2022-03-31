package com.example.demo.controller;

//import com.example.demo.entity.VoucherEntity;
import com.example.demo.event.VoucherAppEvent;
import com.example.demo.exception.Exceptions;
import com.example.demo.exception.VoucherException;
import com.example.demo.model.ActionModel;
import com.example.demo.model.VoucherModel;
import com.example.demo.service.VoucherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@Validated
public class VoucherController {

    private final Logger log = LogManager.getLogger(VoucherController.class);
    @Autowired
    VoucherService voucherService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    Exceptions exceptions;

    @PostMapping("/voucher")
    public ResponseEntity<Void> createVoucher(@Valid @RequestBody VoucherModel voucherModel){

        try {
            voucherService.createVoucher(voucherModel);
            VoucherAppEvent voucherAppEvent = new VoucherAppEvent(this , voucherService);
            eventPublisher.publishEvent(voucherAppEvent);
            log.info("listeners finished their tasks");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (VoucherException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/voucher/{id}")
    public ResponseEntity<Object> updateVoucher(@Valid @RequestBody VoucherModel voucherModel ,@Valid @PathVariable Long id) {
        try {
            return voucherService.updateVoucher(voucherModel , id);
        } catch (VoucherException e) {
            return exceptions.handleVoucherNotFound(e);
        }
    }

    @GetMapping("/voucher/{id}")
    public ResponseEntity<Object> getVoucherById(@Valid @PathVariable("id") Long id) {
        try {
            return voucherService.getVoucherByIdVoucher(id);
        } catch (VoucherException e) {
            return exceptions.handleVoucherNotFound(e);
        }
    }

    @DeleteMapping("/voucher/{id}")
    public ResponseEntity<Object> deleteVoucherById(@Valid @PathVariable Long id) {
        try {
            return voucherService.DeleteVoucherByIdVoucher(id);
        } catch (VoucherException e) {
            return exceptions.handleVoucherNotFound(e);
        }
    }

    @GetMapping("/vouchers")
    public ResponseEntity<Object> getAllVouchers() {
        try{
            return voucherService.getAllVouchers();
        }
        catch (VoucherException e){
            return exceptions.handleVoucherNotFound(e);
        }

    }
    @GetMapping("/voucher/serialNumber/{id}")
    public ResponseEntity<Object> getVoucherSerialNumberById(@Valid @PathVariable Long id) {
        Map result = voucherService.getVoucherSerialNumberById(id);
        return new ResponseEntity<>(result , HttpStatus.OK);
    }

    @GetMapping("/voucher/amount/{id}")
    public ResponseEntity<Object> getVoucherDiscountAmountById(@Valid @PathVariable Long id) {
        Map discountAmount = voucherService.findDiscountAmount(id);
        return new ResponseEntity<>(discountAmount , HttpStatus.OK);
    }
}

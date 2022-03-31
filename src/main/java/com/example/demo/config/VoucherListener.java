package com.example.demo.config;

import com.example.demo.event.VoucherAppEvent;
import com.example.demo.service.VoucherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableAsync
@Configuration
public class VoucherListener {

    private final Logger log = LogManager.getLogger(VoucherListener.class);

    @Async
    @EventListener
//    @Order(1)
    public void listener1(VoucherAppEvent voucherAppEvent) throws InterruptedException {
        log.info("inside listener 1");
//        Thread.sleep(5000);
        log.info("listener 1 finished");
    }

    @Async
    @EventListener
//    @Order(2)
    public void listener2(VoucherAppEvent voucherAppEvent) throws InterruptedException {
        log.info("inside listener 2");
//        Thread.sleep(5000);
        log.info("listener 2 finished");
    }
}

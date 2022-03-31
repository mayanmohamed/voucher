package com.example.demo.scheduledTasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTask {

//    private final Logger log = LogManager.getLogger(ScheduledTask.class);
//
//    @Scheduled(fixedDelay = 2000 , initialDelay = 2000)
//    public void timeLogger(){
//        log.info("the time is : " + System.currentTimeMillis());
//    }
//
//    @Scheduled(fixedDelay = 2000 , initialDelay = 2000)
//    public void dayLogger(){
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//
//        log.info("the day is : " + dtf.format(now));
//    }

}

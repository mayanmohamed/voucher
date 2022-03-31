package com.example.demo.aspect;

import com.example.demo.service.VoucherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* save(*))")
    public void beforeAddEntity() {
        log.info("before creating an entity");
    }

    @AfterReturning("execution(* save(*))")
    public void afterReturnAddEntity() {
        log.info("successfully created an entity");
    }

    @AfterThrowing("execution(* save(*))")
    public void afterThrowingAddEntity() {
        log.info("failed to create an entity");
    }

    @Before("execution(* update(*))")
    public void beforeUpdateEntity() {
        log.info("before updating an entity");
    }

    @AfterReturning("execution(* update(*))")
    public void afterReturnUpdateEntity() {
        log.info("successfully updated the entity");
    }

    @AfterThrowing("execution(* update(*))")
    public void afterThrowingAUpdateEntity() {
        log.info("failed to update the entity");
    }

    @Around("@annotation(com.example.demo.annotation.LogExecutionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }
}

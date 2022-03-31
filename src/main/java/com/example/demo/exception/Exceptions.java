package com.example.demo.exception;

import com.example.demo.model.ActionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class Exceptions {
    @ExceptionHandler
    public ResponseEntity<Object> handleVoucherNotFound(VoucherException ex){
        ActionModel actionModel = new ActionModel();
        actionModel.setCode(HttpStatus.NOT_FOUND.value());
        actionModel.setMessage(ex.getMessage());

        return new ResponseEntity<>(actionModel , HttpStatus.NOT_FOUND);
    }
}

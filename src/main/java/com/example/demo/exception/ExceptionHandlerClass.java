package com.example.demo.exception;

import com.example.demo.model.ActionModel;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler({MethodArgumentNotValidException.class , MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ActionModel showIllegalArgumentCustomMessage(){

        ActionModel actionModel = new ActionModel();
        actionModel.setMessage("your input is invalid");
        actionModel.setCode(HttpStatus.BAD_REQUEST.value());
        return actionModel;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({SQLIntegrityConstraintViolationException.class, javax.validation.ConstraintViolationException.class} )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ActionModel showConstraintViolationCustomMessage(){

        ActionModel actionModel = new ActionModel();
        actionModel.setMessage("constraint violation");
        actionModel.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return actionModel;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({EmptyResultDataAccessException.class , NoSuchElementException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ActionModel showNotFoundCustomMessage(){

        ActionModel actionModel = new ActionModel();
        actionModel.setMessage("entity doesn't exist");
        actionModel.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return actionModel;
    }
}

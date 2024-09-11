package com.lpMarket.controller;

import com.lpMarket.exception.ExistingMemberException;
import com.lpMarket.exception.NotEnoughStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotEnoughStockException.class)
    public String notEnoughStockExHandler(NotEnoughStockException ex , Model model){

        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("remainingStock", ex.getRemainingStock());

        return "error/notEnoughStockException";
    }

    @ExceptionHandler(ExistingMemberException.class)
    public String existingMemberException(ExistingMemberException ex, Model model){

        model.addAttribute("errorMessage",ex.getMessage());

        return "error/existingMemberException";
    }




}

package com.example.activities.web;

import com.example.activities.exception.PassNotMatchingException;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlerInvalid(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashedMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PassNotMatchingException.class)
    public String handleRegException(PassNotMatchingException exception,
                                                  RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", exception.getMessage());
        return "redirect:/registration";
    }
}

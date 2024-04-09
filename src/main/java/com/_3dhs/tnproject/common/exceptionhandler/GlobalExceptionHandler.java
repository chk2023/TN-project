package com._3dhs.tnproject.common.exceptionhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSourceAccessor messageSourceAccessor;

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        // 중복 키 제약 위반 예외 처리
        String errorMessage = messageSourceAccessor.getMessage("member.blockAlreadyBlocked");
        return ResponseEntity.ok(errorMessage);
    }
//    @ExceptionHandler(Exception.class)
//    public String errorView(Exception e, Model model) {
//
//        e.printStackTrace();
//
//        model.addAttribute("errorMessage", e.getMessage());
//
//        return "/error/common";
//    }
}

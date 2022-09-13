package com.fkp.handler;

import com.fkp.constant.ResponseCodeConstant;
import com.fkp.param.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理bean validation 异常
     * @param e 异常对象
     * @return 统一返回错误信息
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public BaseResponse<?> exceptionHandler(BindException e){
        StringBuilder sb = new StringBuilder();
        try {
            for (FieldError error : e.getFieldErrors()) {
                log.error("GlobalExceptionHandler--location:{}#{}--message:{}",error.getObjectName(),error.getField(),error.getDefaultMessage());
                String message = error.getDefaultMessage();
                if (StringUtils.isNotBlank(message)) {
                    sb.append(message).append(";");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return BaseResponse.fail(ResponseCodeConstant.GLOBAL_INNER_EXCEPTION, "inner error:" + ex.getMessage());
        }
        return BaseResponse.fail(ResponseCodeConstant.GLOBAL_EXCEPTION,sb.toString());
    }

    /**
     * 处理方法参数的校验，类上加@Validate,校验在方法参数上
     * @param e 异常对象
     * @return 统一返回错误信息
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public BaseResponse<?> exceptionHandler2(ConstraintViolationException e){
        StringBuilder sb = new StringBuilder();
        try {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                log.error("GlobalExceptionHandler--location:{}#{}--message:{}", violation.getRootBeanClass(), violation.getPropertyPath(),violation.getMessage());
                String message = violation.getMessage();
                if (StringUtils.isNotBlank(message)) {
                    sb.append(message).append(";");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return BaseResponse.fail(ResponseCodeConstant.GLOBAL_INNER_EXCEPTION,"inner error:" + ex.getMessage());
        }
        return BaseResponse.fail(ResponseCodeConstant.GLOBAL_EXCEPTION,sb.toString());
    }

}

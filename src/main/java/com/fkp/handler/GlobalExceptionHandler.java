package com.fkp.handler;

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
            return BaseResponse.fail("888888", "inner error:" + ex.getMessage());
        }
        return BaseResponse.fail("999999",sb.toString());
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
            return BaseResponse.fail("888888","inner error:" + ex.getMessage());
        }
        return BaseResponse.fail("999999",sb.toString());
    }


    //{xxx.xxx.xxx}
//    private static final Pattern CLASS_PATTERN = Pattern.compile("^\\{([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*}$");

//    @ExceptionHandler(value = {ConstraintViolationException.class})
//    public BaseResponse<?> exceptionHandler2(ConstraintViolationException e){
//        StringBuilder sb = new StringBuilder();
//        try {
//            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
//                log.error("GlobalExceptionHandler--location:{}#{}--message:{}", violation.getRootBeanClass(), violation.getPropertyPath(),violation.getMessage());
//                String messageTemplate = violation.getMessageTemplate();
//                if (messageTemplate != null && CLASS_PATTERN.matcher(messageTemplate).matches()){
//                    String path = violation.getPropertyPath().toString();
//                    int index = path.lastIndexOf(".");
//                    if(path.length() - 1 > index){
//                        //path:方法名.参数名，截取参数名
//                        sb.append(path.substring(index + 1)).append(violation.getMessage()).append(";");
//                    }else {
//                        throw new RuntimeException("PropertyPath Illegal");
//                    }
//                }else {
//                    sb.append(violation.getMessage()).append(";");
//                }
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//            return BaseResponse.fail("888888","inner error:" + ex.getMessage());
//        }
//        return BaseResponse.fail("999999",sb.toString());
//    }
}

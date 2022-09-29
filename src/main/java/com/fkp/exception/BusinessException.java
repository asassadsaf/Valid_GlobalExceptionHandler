package com.fkp.exception;

import com.fkp.constant.ErrorCodeEnum;

public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 3319486522727680695L;
    private final String errorCode;

    public BusinessException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode){
        super(ErrorCodeEnum.getMessage(errorCode));
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

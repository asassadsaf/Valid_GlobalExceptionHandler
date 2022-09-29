package com.fkp.param;

import com.fkp.constant.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.regex.Pattern;

public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 2621638727631497840L;
    private String code;
    private String status;
    private String msg;
    private T data;
    private static final String CODE_SUCCESS = "000000";
    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_FAIL = "fail";
    private static final String MESSAGE_SUCCESS = "operation success";

    public BaseResponse() {
    }

    public BaseResponse(String code, String status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public BaseResponse(String code, String status, String msg, T data) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(){
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(CODE_SUCCESS);
        res.setStatus(STATUS_SUCCESS);
        res.setMsg(MESSAGE_SUCCESS);
        return res;
    }

    public static <T> BaseResponse<T> success(String msg){
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(CODE_SUCCESS);
        res.setStatus(STATUS_SUCCESS);
        res.setMsg(msg);
        return res;
    }

    public static <T> BaseResponse<T> success(T data){
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(CODE_SUCCESS);
        res.setStatus(STATUS_SUCCESS);
        res.setMsg(MESSAGE_SUCCESS);
        res.setData(data);
        return res;
    }

    public static <T> BaseResponse<T> success(String msg, T data){
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(CODE_SUCCESS);
        res.setStatus(STATUS_SUCCESS);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    public static <T> BaseResponse<T> fail(String code){
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(code);
        res.setStatus(STATUS_FAIL);
        String message = ErrorCodeEnum.getMessage(code);
        if(StringUtils.isBlank(message)){
            message = ErrorCodeEnum.UnKnowError.getMsg();
        }
        res.setMsg(message);
        return res;
    }

    public static <T> BaseResponse<T> fail(String code, String msg){
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(code);
        res.setStatus(STATUS_FAIL);
        res.setMsg(msg);
        return res;
    }

    public static <T> BaseResponse<T> fail(String code, String msg, T data){
        BaseResponse<T> res = new BaseResponse<>();
        res.setCode(code);
        res.setStatus(STATUS_FAIL);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}

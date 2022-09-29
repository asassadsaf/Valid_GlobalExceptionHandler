package com.fkp.constant;

import lombok.Getter;
import org.springframework.transaction.CannotCreateTransactionException;

@Getter
public enum ErrorCodeEnum {
    //校验失败
    ValidException("999999", "Global Exception"),

    //全局异常捕获内部异常
    GlobalInnerException("999998", "Global Exception Inner Error"),

    //业务异常
    BusinessException("999983", "Business Exception"),

    //连接异常
    NetworkError("999997", "Network Error"),

    //数据库连接异常
    DatabaseException("999996", "Database Error"),

    //空指针异常
    NullPointerException("999995", "Null Point Exception"),

    //IO异常
    IOException("999994", "IO Exception"),

    //参数格式错误
    ParamsInvalid("400", "Parameter Format Error"),

    //参数缺失错误
    ParamMissing("400", "Parameter Missing"),

    //找不到资源
    NoHandlerFoundException("404", "404 Not Found"),

    //不支持的请求方法
    HttpRequestMethodNotSupportedException("405", "Http Request Method Not Support"),

    //当请求处理程序无法生成客户端可接受的响应时引发异常
    HttpMediaTypeNotAcceptableException("406", "Not Acceptable"),

    //运行时异常
    RuntimeException("500", "Runtime Exception"),

    //其他异常
    OtherException("999991", "Other Exception"),

    //无法使用JTA等底层事务API创建事务时引发异常
    CannotCreateTransactionException("999990", "Transaction Exception"),

    //遇到一般事务系统错误时抛出异常，例如提交或回滚
    TransactionSystemException("999989", "Transaction System Exception"),

    //类型转换异常
    ClassCastException("999988", "Class Cast Exception"),

    //未知方法异常
    NoSuchMethodException("999987", "No Such Method Exception"),

    //数组越界异常
    IndexOutOfBoundsException("999986", "Index Out Of Bounds Exception"),

    //尝试设置 bean 属性时在类型不匹配时引发异常
    TypeMismatchException("400", "Type Mismatch Exception"),

    //找不到适合 bean 属性的编辑器或转换器时抛出异常
    ConversionNotSupportedException("500", "Not Acceptable"),

    //当HttpMessageConverter.write方法失败时由HttpMessageConverter实现抛出
    HttpMessageNotWritableException("999984", "Not Acceptable"),

    //未知错误
    UnKnowError("999982", "UnKnow Error!"),

    //415错误，客户端请求的类型服务端不支持
    HttpMediaTypeNotSupportedException("415", "Unsupported Media Type");










    private final String code;
    private final String msg;

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMessage(String code){
        String msg = "";
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (value.code.equals(code)) {
                msg = value.msg;
                break;
            }
        }
        return msg;
    }

    public static ErrorCodeEnum getErrorCode(String code){
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}

package com.fkp.controller;

import com.fkp.param.BaseResponse;
import com.fkp.service.TestService5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test5", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController5 {

    @Autowired
    private TestService5 service;

    @RequestMapping(value = "/method1")
    public BaseResponse<String> method1(){
        return service.serviceMethod1();
    }

    @RequestMapping(value = "/method2")
    public BaseResponse<String> method2(){
        return service.serviceMethod2();
    }
}

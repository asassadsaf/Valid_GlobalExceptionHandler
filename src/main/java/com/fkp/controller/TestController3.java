package com.fkp.controller;

import com.fkp.param.BaseResponse;
import com.fkp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在Service层使用校验框架
 */
@RestController
@RequestMapping(value = "test3")
public class TestController3 {

    @Autowired
    TestService testService;

    @RequestMapping(value = "user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<?> user(String name){
        return testService.user(name);
    }





}

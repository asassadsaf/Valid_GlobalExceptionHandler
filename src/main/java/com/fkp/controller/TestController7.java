package com.fkp.controller;

import com.alibaba.fastjson.JSON;
import com.fkp.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fkp
 * 测试传入的参数和请求实体属性类型不匹配时如何使用默认值
 */
@RestController
@RequestMapping(value = "/test7")
public class TestController7 {

    @PostMapping(value = "/save")
    public String save(@RequestBody User user){
        System.out.println(user);
        return JSON.toJSONString(user);
    }
}

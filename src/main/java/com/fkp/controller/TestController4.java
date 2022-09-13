package com.fkp.controller;

import com.fkp.entity.Person;
import com.fkp.param.BaseResponse;
import com.fkp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在Service层实现分组校验
 * 在做增删改查时，可能对同一个实体类中的属性有不同的校验规则
 *      例如在对人员进行增删改查时，新增接口对人员实体的校验规则可能是id,name,age都不为空，而修改接口对人员实体的校验规则可能是id不为空即可
 *      此时定义两个分组，新增时的校验分组和修改时的校验分组，对不同的操作应用不同的校验规则
 */
@RestController
@RequestMapping(value = "/test4")
public class TestController4 {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<?> add(Person person){
        return personService.add(person);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<?> modify(Person person){
        return personService.modify(person);
    }

    @RequestMapping(value = "/modify2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<?> modify2(Person person){
        return personService.modify2(person);
    }



}

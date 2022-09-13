package com.fkp.service;

import com.fkp.entity.Person;
import com.fkp.param.BaseResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 在Service层分组校验,需要在接口上添加@Validated注解,
 * 若是分组校验则需要在方法上使用@Validator注解指定分组并在参数前添加@Valid注解
 * 若不是分组校验则直接在参数前使用@Valid注解
 * 在不同的方法上指定不同分组的校验规则
 */
@Validated
public interface PersonService {

    /**
     * 新增规则
     * @param person
     * @return
     */
    @Validated(value = {Person.PersonAddGroup.class})
    BaseResponse<?> add(@Valid Person person);

    /**
     * 修改规则
     * @param person
     * @return
     */
    @Validated(value = {Person.PersonModifyGroup.class})
    BaseResponse<?> modify(@Valid Person person);

    /**
     * 不分组校验
     * @param person
     * @return
     */
    BaseResponse<?> modify2(@Valid Person person);
}

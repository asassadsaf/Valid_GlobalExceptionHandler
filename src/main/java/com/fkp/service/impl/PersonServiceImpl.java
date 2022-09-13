package com.fkp.service.impl;

import com.fkp.entity.Person;
import com.fkp.param.BaseResponse;
import com.fkp.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Override
    public BaseResponse<?> add(Person person) {
        log.info("新增人员信息：{}",person);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse<?> modify(Person person) {
        log.info("修改人员信息：{}",person);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse<?> modify2(Person person) {
        log.info("不分组校验，修改人员信息：{}",person);
        return BaseResponse.success();
    }
}

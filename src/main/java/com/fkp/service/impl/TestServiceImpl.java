package com.fkp.service.impl;

import com.fkp.param.BaseResponse;
import com.fkp.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public BaseResponse<?> user(String name) {
        return BaseResponse.success(name);
    }
}

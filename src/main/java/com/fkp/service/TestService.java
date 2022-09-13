package com.fkp.service;

import com.fkp.param.BaseResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 在Service层校验和在Controller层校验的方式一样，在Service层校验更加可靠
 * 请把校验注解写在Service接口上而不是实现类
 */
@Validated
public interface TestService {

    BaseResponse<?> user(@NotBlank(message = "name not be blank!") String name);
}

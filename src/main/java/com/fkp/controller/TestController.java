package com.fkp.controller;

import com.fkp.param.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 1.方法参数前加校验注解，需要在类上添加@Validated开启校验，校验不通过抛ConstraintViolationException
 * 2.在实体中添加校验注解，只需在方法参数前添加@Validated或@Valid
 * 3.若实体中还有实体，想要校验实体中实体的属性需要在实体的实体属性上添加@Valid注解，代表嵌套校验
 * 4.若在一个Controller中即有参数前加校验注解的又有实体中加校验注解的，则此时类上会有@Validated注解，而方法参数为实体的参数前会加@Valid，
 *      因为类上的@Validated注解并不会开启方法参数是实体，校验注解在实体中的校验，需要在参数前添加@Valid注解开启校验，那么在这种情况下
 *      在校验完实体类中属性值后还会根据@Validated的规则进行校验，造成资源浪费，所以如果Controller中只存在在校验注解添加在实体中的情况则
 *      不需要在类上添加@Validated，防止校验两次而消耗性能。
 */
@RestController
@RequestMapping(value = "/test")
@Validated
public class TestController {
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<User> user(@RequestParam("name") @NotEmpty String name){
        User user = new User();
        user.setName(name);
        return new BaseResponse<User>("000000","success","000000", user);
    }

    @RequestMapping(value = "/user2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<String>> user2(@RequestBody @NotEmpty List<String> name){
        return new BaseResponse<List<String>>("000000","success","000000", name);
    }

    /**
     * 4中说明的即是只在类上添加@Validated不会开始User类中name的校验，需要在参数前添加@Valid开启，类似实体中有实体开启嵌套校验
     * 若一个Controller中只有类似此种方法的校验，则不需要再类上添加@Validated注解，因为添加后也会走@Validated注解的MethodValidationInterceptor的拦截器，造成浪费
     * @param user
     * @return
     */
    @RequestMapping(value = "/user3", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<User> user3(@RequestBody @Valid User user){
        return new BaseResponse<User>("000000","success","000000", user);
    }

    /**
     * 此种方式和user3的方式一样
     * @param user
     * @return
     */
    @RequestMapping(value = "/user4", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<User> user4(@RequestBody @Validated User user){
        return new BaseResponse<User>("000000","success","000000", user);
    }


    static class User{
        @NotBlank(message = "name not be blank!")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

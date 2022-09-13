package com.fkp.controller;

import com.fkp.param.BaseResponse;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/test2")
public class TestController2 {
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    //校验方法参数中实体内的属性，需要在参数前加@Valid或@Validated，校验不通过时，
    // 若以表单提交(不加@RequestBody使用postman的Body中x-www-form-urlencoded方式)抛出BindException
    // 若以json提交(加@RequestBody)抛出MethodArgumentNotValidException
    public BaseResponse<User> user(@Valid User user2){
        return new BaseResponse<User>("000000","success","000000", user2);
    }

    @RequestMapping(value = "/user2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<User> user2(@RequestBody @Valid User user2){
        return new BaseResponse<User>("000000","success","000000", user2);
    }

    /**
     * 实体中加校验注解的，在参数前加@Validated或@Valid都可以开启校验
     * @param user2
     * @return
     */
    @RequestMapping(value = "/user3", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<User> user3(@RequestBody @Validated User user2){
        return new BaseResponse<User>("000000","success","000000", user2);
    }


    static class User{
        @NotBlank(message = "name not be blank")
        private String name;

        @NotNull(message = "age not be null")
        @Range(min = 18, max = 30, message = "age must between 18 and 30")
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}

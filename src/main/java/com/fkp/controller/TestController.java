package com.fkp.controller;

import com.fkp.param.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/test")
//校验方法的参数需要在类上加此注解，校验不通过抛ConstraintViolationException
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


    static class User{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

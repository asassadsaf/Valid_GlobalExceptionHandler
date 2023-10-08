package com.fkp.entity;

import lombok.Data;

@Data
public class User {
    private String name;

    private Integer age;

    public User() {
        System.out.println("struct..");
    }
    //想在请求体中传入的age是空字符串时使用age的默认值
    //json封装对象本质还是调用属性的setter方法，可以通过改造setter方法实现
    public void setAge(Integer age) {
        if(age == null){
            this.age = 18;
        }else {
            this.age = age;
        }
    }
}

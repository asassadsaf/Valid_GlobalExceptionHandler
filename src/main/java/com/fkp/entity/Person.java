package com.fkp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * 定义分组，即空的接口
 * 划分分组，使用groups属性,默认分组为Default.class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @NotBlank(groups = {PersonAddGroup.class, PersonModifyGroup.class, Default.class}, message = "id not be blank!")
    private String id;
    @NotBlank(groups = {PersonAddGroup.class, Default.class}, message = "name not be blank!")
    private String name;
    @NotNull(groups = {PersonAddGroup.class, Default.class}, message = "age not be null!")
    private Integer age;

    public interface PersonAddGroup{}
    public interface PersonModifyGroup{}
}

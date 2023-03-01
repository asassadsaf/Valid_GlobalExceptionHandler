package com.fkp.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 测试mvc框架反序列化实体类
 * 序列化和反序列化本质上是通过实体的getter和setter方法实现的
 * 1.jackson在是通过getter和setter方法方法名来对应属性名，使用lombok实现的getter和setter对于单个首字母小写的属性默认为大写，例如dInfo对应getDInfo,setDInfo,jsckson在识别时默认将get和set后识别到的大写字母转为小写，直到识别到小写字母后将按照原属性名识别
 * 2.fastjson则通过实际的属性名来对应
 * Jackson默认的属性发现规则将会查找到如下所述的属性：
 *      1.所有被public修饰的字段（成员变量）；
 *      2.所有被public修饰的getter（即形如“getXxx()”的方法）；
 *      3.所有被public修饰的setter（即形如“setXxx(value)”的方法）。
 *      字段名是通过get方法名转换过来的, 所以会出现字母大小写问题（当前几个都是大写的字母，都会转换成小写，直到不是大写为止，若小写后面还有大写，则保持大写。）, 以及如果用@JsonProperty("")强制序列化为某个名称时, 如果不在get/set方法上加@JsonIgnore则会出现两个字段,一个首字母大写, 一个首字母小写。可以将属性用private修饰，在get方法上加@JsonProperty,然而使用lombok自动生成的getter,setter方法没有重复字段的情况，即使自动生成的方法也没办法加@JsonIgnore注解
 * 可以在实体类上添加注解@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
 *      1.JsonAutoDetect.Visibility.ANY : 表示所有字段都可以被发现, 包括private修饰的字段, 解决大小写问题；
 *      2.JsonAutoDetect.Visibility.NONE : 表示get方法不可见,解决字段重复问题。若使用@JsonAutoDetect注解，则使用lombok也需解决字段重复问题，需要添加该属性
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString
//让jackson也能发现private修饰的属性，从而解决单个首字母小写转换为全部小写的问题，屏蔽自动生成的get方法，从而解决字段重复问题，即getDInfo -> dinfo, dInfo -> dInfo
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Device {
    //全小写属性
    private String id;
    //正常的小驼峰命名
    private String deviceName;
    //单个首字母小写的命名
//    @JsonProperty(value = "dInfo")  //使用该注解指定序列化和反序列化时的名称
    private String dInfo;
}

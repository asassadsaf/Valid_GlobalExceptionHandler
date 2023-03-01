package com.fkp.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkp.entity.Device;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController6 {

    /**
     * 测试mvc反序列化
     * 请求参数：{"id":"001","deviceName":"testDevice1","dInfo":"test"}
     * @param device 封装参数
     *
     * springboot默认使用jackson作为序列化工具
     * 1.jackson对反序列化单个首字母小写的属性不友好，参数封装结果为Device(id=001, deviceName=testDevice1, dInfo=null)
     * 2.将MessageConverter更换为fastjson实现后没有此问题，参数封装结果为Device(id=001, deviceName=testDevice1, dInfo=test)
     */
    @RequestMapping(value = "/deSerialTest", method = RequestMethod.POST)
    public void deSerialTest(@RequestBody Device device){
        System.out.println(device);
    }

    /**
     * 测试mvc序列化
     * @return 序列化对象
     *
     *1.jackson序列化结果将单个首字母小写的属性序列化为全部小写，响应结果为{"id":"001","deviceName":"testDevice1","dinfo":"test"}
     * 2.将MessageConverter更换为fastjson实现后没有此问题，响应结果为{"id":"001","deviceName":"testDevice1","dInfo":"test"}
     */
    @RequestMapping(value = "/serialTest", method = RequestMethod.GET)
    public Device serialTest(){
        return new Device("001", "testDevice1", "test");
    }

    public static void main(String[] args) throws JsonProcessingException {
        Device device = new Device("001", "testDevice1", "test");
        String s = JSON.toJSONString(device);
        System.out.println("serialDevice:" + s);
        Device deSerialDevice = JSON.parseObject(s, Device.class);
        System.out.println(deSerialDevice);

        ObjectMapper objectMapper = new ObjectMapper();
        String s2 = objectMapper.writeValueAsString(device);
        System.out.println("Jackson serialDevice:" + s2);
        Device deSerialDevice2 = objectMapper.readValue(s2, Device.class);
        System.out.println(deSerialDevice2);

    }
}

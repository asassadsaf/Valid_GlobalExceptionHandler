package com.fkp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkp.entity.Car;
import com.fkp.entity.Device;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>jackson序列化反序列化首字母单个小写的属性，根据规则，如果序列化和反序列化都保持相同的规则也没问题</p>
 * <p>例如都加了@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)或都不加时没问题，如果规则不同，则可能会导致反序列化失败。</p>
 */
public class SerializationTest {

    @Test
    void testJacksonSerial() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Device device = new Device("1","deviceTest", "abc");
        String s = mapper.writeValueAsString(device);
        FileWriter writer = new FileWriter("files/serialFile.txt");
        writer.write(s);
        writer.close();
    }

    @Test
    void testJacksonDeserial() throws IOException {
        FileReader reader = new FileReader("files/serialFile.txt");
        BufferedReader br = new BufferedReader(reader);
        String s = br.readLine();
        System.out.println(s);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果不加以上配置，当无法转换为对象属性时会报错
        Device device = mapper.readValue(s, Device.class);
        System.out.println(device);
    }

    @Test
    void testFastJsonSerialUppercase(){
        Car car = Car.builder().CarId(1).CarName("audi").build();
        //添加new PascalNameFilter()将属性首字母大写系列化，SerializerFeature.WriteMapNullValue将null值序列化
        //通过NameFilter.of(PropertyNamingStrategy.PascalCase)可选择不同的名字过滤器选项
        String jsonString = JSON.toJSONString(car, new PascalNameFilter(), SerializerFeature.WriteMapNullValue);
        System.out.println(jsonString);
    }

}

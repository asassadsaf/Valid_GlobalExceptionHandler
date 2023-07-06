package com.fkp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkp.entity.Device;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

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

}

package com.fkp;

import com.fkp.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ValidGlobalExceptionHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidGlobalExceptionHandlerApplication.class, args);
    }

}

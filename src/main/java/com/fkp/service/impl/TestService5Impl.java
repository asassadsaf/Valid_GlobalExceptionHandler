package com.fkp.service.impl;

import com.fkp.constant.ErrorCodeEnum;
import com.fkp.constant.ResponseCodeConstant;
import com.fkp.param.BaseResponse;
import com.fkp.service.TestService5;
import org.springframework.stereotype.Service;

@Service
public class TestService5Impl implements TestService5 {
    private final Object lock = new Object();

    @Override
    public synchronized BaseResponse<String> serviceMethod1() {
        System.out.println("wait 10s begin...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wait 10s end...");
        return BaseResponse.success("success", "serviceMethod1 response!");
    }

    @Override
    public BaseResponse<String> serviceMethod2() {
//        synchronized (lock){
            //        new Thread(() -> {
//            System.out.println("wait 5s begin...");
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("serviceMethod2 方法执行....");
//            System.out.println("wait 5s end...");
//        }).start();
            System.out.println("loop start..." + Thread.currentThread().getName());
//        long i = 0;
//        while (i<100000000000L){
//            i++;
//        }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("loop end....");
            return BaseResponse.success("success", "serviceMethod2 response!");

//        }
    }
}

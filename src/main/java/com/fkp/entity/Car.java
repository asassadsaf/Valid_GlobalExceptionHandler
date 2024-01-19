package com.fkp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fengkunpeng
 * @version 1.0
 * @description
 * @date 2024/1/19 16:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Integer CarId;
    private String CarName;
    private Integer CarPrice;
}

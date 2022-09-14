package com.fkp.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> implements Serializable {
    private static final long serialVersionUID = -1078779649472488950L;
    private int pageNum;
    private int pageSize;
    private long total;
    private List<T> list;
}

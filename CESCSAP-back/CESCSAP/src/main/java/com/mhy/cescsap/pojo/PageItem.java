package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageItem<T>{
    // 总共有多少条
    private Long total;
    // 当前页的数据
    private List<T> list;
}

package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class URL {
    //编号
    private Long urlId;
    //链接
    private String url;

    public URL(String url) {
        this.url = url;
    }
}

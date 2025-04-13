package com.mhy.cescsap.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientErrorLogDTO {
    private String timestamp;
    private String url;
    private String userAgent;
    private String type;      // error / unhandledrejection / axios
    private String message;
    private String stack;
    private Map<String, Object> config;
    private Map<String, Object> response;
}

package com.baizhi.xzf.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    private String status;
    private String message;
    private Object data;


}


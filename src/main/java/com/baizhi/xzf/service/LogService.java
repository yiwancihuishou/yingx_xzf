package com.baizhi.xzf.service;

import java.io.IOException;
import java.util.HashMap;

public interface LogService {
    HashMap<String,Object> queryByPage(Integer page, Integer rows);
    void outPutLogExcel() throws IOException;
}

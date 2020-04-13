package com.baizhi.xzf.service;

import com.baizhi.xzf.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    HashMap<String,Object> login(Admin admin, String code);
}

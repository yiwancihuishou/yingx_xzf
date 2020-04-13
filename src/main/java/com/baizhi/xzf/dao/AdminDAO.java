package com.baizhi.xzf.dao;

import com.baizhi.xzf.entity.Admin;

public interface AdminDAO {
    Admin queryUsername(String username,String password);
}

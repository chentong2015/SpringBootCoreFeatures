package com.chentong.myblog.service;

import com.chentong.myblog.model.User;

/**
 * Service层 ：业务逻辑层 ===> 链接Dao层，关系数据库
 * 该接口用于处理数据库链接的User表格
 */
public interface UserService {

    User checkUser(String username, String password);

}

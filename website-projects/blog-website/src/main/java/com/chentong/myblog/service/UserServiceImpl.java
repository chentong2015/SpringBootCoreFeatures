package com.chentong.myblog.service;

import com.chentong.myblog.dao.UserRepository;
import com.chentong.myblog.model.User;
import com.chentong.myblog.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 接口类的实现 implements ***
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        //查询加密后的密码，在网络中只用密码进行传输，避免使用明码
        User user = userRepository.findByUsernameAndPassword(username, Md5Util.code(password));
        return user;
    }

}

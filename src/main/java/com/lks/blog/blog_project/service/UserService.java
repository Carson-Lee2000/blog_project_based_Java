package com.lks.blog.blog_project.service;

import com.lks.blog.blog_project.dao.UserMapper;
import com.lks.blog.blog_project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

}

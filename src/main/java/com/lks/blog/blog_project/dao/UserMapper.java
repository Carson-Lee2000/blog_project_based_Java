package com.lks.blog.blog_project.dao;

import com.lks.blog.blog_project.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    /**
     * get user by id
     * @param id user id
     * @return user object
     */
    User selectById(@Param("id")int id);

    User selectByName(@Param("username")String username);

    User selectByEmail(@Param("email")String email);

    int insertUser(User user);

    int updateStatus(@Param("id")int id, @Param("status")int status);

    int updateHeader(@Param("id")int id, @Param("headerUrl")String headerUrl);

    int updatePassword(@Param("id")int id,@Param("password") String password);

}

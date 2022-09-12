package com.lks.blog.blog_project.dao;

import com.lks.blog.blog_project.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {

    /**
     *  新增一条登录凭证
     * @param loginTicket
     * @return
     */
    @Insert({
            "insert into login_ticket(user_id, ticket, status, expired) ",
             "values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id, user_id, ticket, status, expired ",
            "from login_ticket where ticket = #{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    /**
     * 修改登录状态
     * @param ticket
     * @param status
     * @return
     */
    @Update({
            "<script>",
            "update login_ticket set status = #{status} ",
            "where ticket = #{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1 = 1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);
}

package com.lks.blog.blog_project.dao;

import com.lks.blog.blog_project.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    // 查询当前用户的会话列表，针对每个会话只返回一条最新的私信（不同人发给当前用户的私信）
    List<Message> selectConversations(@Param("userId")int userId, @Param("offset")int offset, @Param("limit")int limit);

    // 查询当前用户的会话数量
    int selectConversationCount(@Param("userId")int userId);

    // 查询某个会话所包含的私信列表（某个特定的人发给当前用户的私信）
    List<Message> selectLetters(@Param("conversationId")String conversationId, @Param("offset")int offset,@Param("limit") int limit);

    // 查询某个会话所包含的私信数量
    int selectLetterCount(@Param("conversationId")String conversationId);

    // 查询未读消息数量
    int selectLetterUnreadCount(@Param("userId")int userId, @Param("conversationId")String conversationId);

    // 增加私信
    int insertMessage(Message message);

    // 更改消息状态：未读->已读
    int updateStatus(@Param("ids")List<Integer> ids,@Param("status") int status);
}

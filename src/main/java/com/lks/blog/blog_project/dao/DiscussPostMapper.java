package com.lks.blog.blog_project.dao;

import com.lks.blog.blog_project.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    /**
     *
     * @param userId user id: if userId is 0, return all users' discussPosts, if userId is a real user's id, return this user's posts.
     * @param offset the start index of a page
     * @param limit 一页显示的最大数量
     * @return list of discussPosts
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    int selectDiscussPostRows(@Param("userId") int userId);

    // 增加帖子
    int insertDiscussPost(DiscussPost discussPost);

    // 查询帖子详情
    DiscussPost selectDiscussPostById(int id);
}

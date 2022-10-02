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
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
    // @Param注解用于给参数取别名，只有一个变量可以不加，要是有两个一定要加，所以一般都加上比较保险
    // sql在<if>里使用,则所有参数必须加别名.

    int selectDiscussPostRows(@Param("userId") int userId);

    // 增加帖子
    int insertDiscussPost(DiscussPost discussPost);

    // 查询帖子详情
    DiscussPost selectDiscussPostById(@Param("id") int id);

    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}

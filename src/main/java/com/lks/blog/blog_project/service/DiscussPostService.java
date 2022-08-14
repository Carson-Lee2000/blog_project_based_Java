package com.lks.blog.blog_project.service;

import com.lks.blog.blog_project.dao.DiscussPostMapper;
import com.lks.blog.blog_project.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {

    private final DiscussPostMapper discussPostMapper;

    @Autowired
    public DiscussPostService(DiscussPostMapper discussPostMapper) {
        this.discussPostMapper = discussPostMapper;
    }

    /**
     *
     * @param userId 用户ID，当为0时查询所有合法帖子，为具体的用户ID时返回该用户发布的所有帖子
     * @param offset 当前查询页的起始下标
     * @param limit 查询页最大数量
     * @return post集合
     */
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    /**
     *
     * @param userId 用户ID，当为0时查询所有合法帖子数量，为具体的用户ID时返回该用户发布的所有帖子数量
     * @return post数量
     */
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}

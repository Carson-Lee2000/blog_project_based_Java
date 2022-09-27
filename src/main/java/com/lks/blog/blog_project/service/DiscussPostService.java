package com.lks.blog.blog_project.service;

import com.lks.blog.blog_project.dao.DiscussPostMapper;
import com.lks.blog.blog_project.entity.DiscussPost;
import com.lks.blog.blog_project.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostService {

    private final DiscussPostMapper discussPostMapper;
    private final SensitiveFilter sensitiveFilter;

    @Autowired
    public DiscussPostService(DiscussPostMapper discussPostMapper, SensitiveFilter sensitiveFilter) {
        this.discussPostMapper = discussPostMapper;
        this.sensitiveFilter = sensitiveFilter;
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

    public int addDiscussPost(DiscussPost post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 转义html标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }
}

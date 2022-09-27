package com.lks.blog.blog_project.controller;

import com.lks.blog.blog_project.entity.Comment;
import com.lks.blog.blog_project.service.CommentService;
import com.lks.blog.blog_project.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;
    private final HostHolder hostHolder;

    @Autowired
    public CommentController(CommentService commentService, HostHolder hostHolder) {
        this.commentService = commentService;
        this.hostHolder = hostHolder;
    }

    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussionPostId, Comment comment) {
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        return "redirect:/discuss/detail/" + discussionPostId;
    }
}

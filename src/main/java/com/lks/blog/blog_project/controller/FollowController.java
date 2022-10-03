package com.lks.blog.blog_project.controller;

import com.lks.blog.blog_project.annotation.LoginRequired;
import com.lks.blog.blog_project.entity.User;
import com.lks.blog.blog_project.service.FollowService;
import com.lks.blog.blog_project.util.CommunityUtil;
import com.lks.blog.blog_project.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FollowController {

    private final FollowService followService;
    private final HostHolder hostHolder;

    @Autowired
    public FollowController(FollowService followService, HostHolder hostHolder) {
        this.followService = followService;
        this.hostHolder = hostHolder;
    }

    @LoginRequired
    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUser();

        followService.follow(user.getId(), entityType, entityId);

        return CommunityUtil.getJsonString(0, "已关注！");
    }

    @LoginRequired
    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUser();

        followService.unfollow(user.getId(), entityType, entityId);

        return CommunityUtil.getJsonString(0, "已取消关注！");
    }
}

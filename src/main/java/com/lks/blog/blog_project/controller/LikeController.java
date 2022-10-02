package com.lks.blog.blog_project.controller;

import com.lks.blog.blog_project.entity.User;
import com.lks.blog.blog_project.service.LikeService;
import com.lks.blog.blog_project.util.CommunityUtil;
import com.lks.blog.blog_project.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    private final LikeService likeService;
    private final HostHolder hostHolder;

    @Autowired
    public LikeController(LikeService likeService, HostHolder hostHolder) {
        this.likeService = likeService;
        this.hostHolder = hostHolder;
    }

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId) {
        User user = hostHolder.getUser();

        // 实现点赞
        likeService.like(user.getId(), entityType, entityId);
        // 数量显示
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);

        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        return CommunityUtil.getJsonString(0, null, map);
    }
}

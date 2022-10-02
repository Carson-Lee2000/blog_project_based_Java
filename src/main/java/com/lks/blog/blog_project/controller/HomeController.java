package com.lks.blog.blog_project.controller;

import com.lks.blog.blog_project.entity.DiscussPost;
import com.lks.blog.blog_project.entity.Page;
import com.lks.blog.blog_project.entity.User;
import com.lks.blog.blog_project.service.DiscussPostService;
import com.lks.blog.blog_project.service.LikeService;
import com.lks.blog.blog_project.service.UserService;
import com.lks.blog.blog_project.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements CommunityConstant {

    private final DiscussPostService discussPostService;
    private final UserService userService;
    private final LikeService likeService;

    @Autowired
    public HomeController(DiscussPostService discussPostService, UserService userService, LikeService likeService) {
        this.discussPostService = discussPostService;
        this.userService = userService;
        this.likeService = likeService;
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        page.setRows(discussPostService.findDiscussPostRows(0));
        //
        // System.out.println(page.getRows());
        //
        // System.out.println(page.getTotal());
        //
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);

                // 在首页显示每个帖子的获赞数量
                long likeCount = likeService.findEntityLikeCount(CommunityConstant.ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }

        model.addAttribute("discussPosts", discussPosts);
        return "/index";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }
}

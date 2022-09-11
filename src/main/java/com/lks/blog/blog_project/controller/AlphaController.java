package com.lks.blog.blog_project.controller;

import com.lks.blog.blog_project.util.CommunityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello spring boot";
    }


    // cookie demo
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置cookie的生效范围
        cookie.setPath("/community/alpha");
        // 设置cookie的生存时间s
        cookie.setMaxAge(60 * 10);
        // 发送cookie给浏览器
        response.addCookie(cookie);
        return "set cookie";
    }
}

package com.lks.blog.blog_project.controller;

import com.lks.blog.blog_project.annotation.LoginRequired;
import com.lks.blog.blog_project.entity.User;
import com.lks.blog.blog_project.service.FollowService;
import com.lks.blog.blog_project.service.LikeService;
import com.lks.blog.blog_project.service.UserService;
import com.lks.blog.blog_project.util.CommunityConstant;
import com.lks.blog.blog_project.util.CommunityUtil;
import com.lks.blog.blog_project.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping(path = "/user")
public class UserController implements CommunityConstant{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final UserService userService;
    private final HostHolder hostHolder;
    private final LikeService likeService;
    private final FollowService followService;

    @Autowired
    public UserController(UserService userService, HostHolder hostHolder, LikeService likeService, FollowService followService) {
        this.userService = userService;
        this.hostHolder = hostHolder;
        this.likeService = likeService;
        this.followService = followService;
    }

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    /**
     * 修改上传头像
     * @param headerImage
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片！");
            return "/site/setting";
        }
        
        // 上传选择的图片头像，文件的名字要随机生成
        String fileName = headerImage.getOriginalFilename();
        // 获取文件后缀（扩展名）
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件的格式不正确！");
            return "/site/setting";
        }

        // 生成随机的文件名
        fileName = CommunityUtil.generateUUID() + suffix;

        // 确定文件的存放路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败！" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常", e);
        }

        // 更新当前用户头像的web访问路径
        // http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀解析
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 响应文件
        response.setContentType("image/" + suffix);
        try (
            FileInputStream fis = new FileInputStream(fileName);
            OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败！" + e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @LoginRequired
    @RequestMapping(path = "/changePassword", method = RequestMethod.POST)
    public String changePassword(String oldPassword, String newPassword, String confirmPassword, Model model) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.changePassword(user, oldPassword, newPassword, confirmPassword);
        if (map == null || map.isEmpty()) {
            return "redirect:/index";
        } else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            model.addAttribute("confirmPasswordMsg", map.get("confirmPasswordMsg"));
            return "/site/setting";
        }
    }

    // 个人主页
    // TODO
    @LoginRequired
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String  getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }

        model.addAttribute("user", user);
        // 获赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        // 查询当前用户的关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        // 查询当前用户的粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);
        // 查询当前登录用户是否已经关注打开个人主页的用户
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
            model.addAttribute("hasFollowed", hasFollowed);
        }

        return "/site/profile";
    }
}

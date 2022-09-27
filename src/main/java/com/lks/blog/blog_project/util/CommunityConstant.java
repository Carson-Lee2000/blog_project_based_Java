package com.lks.blog.blog_project.util;

public interface CommunityConstant {

    int ACTIVATION_SUCCESS = 0;
    int ACTIVATION_REPEAT = 1;
    int ACTIVATION_FAILURE = 2 ;

    /**
     * 默认状态下的登录凭证超时时间12h
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 勾选记住我之后，登录凭证超时时间100d
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    /**
     * 实体类型：帖子
     */
    int ENTITY_TYPE_POST = 1;

    /**
     * 实体类型：评论
     */
    int ENTITY_TYPE_COMMENT = 2;
}

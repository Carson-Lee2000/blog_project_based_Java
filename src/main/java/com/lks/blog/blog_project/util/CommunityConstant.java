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

    /**
     * 实体类型：用户
     */
    int ENTITY_TYPE_USER = 3;

    /**
     * Kafka主题：评论
     */
    String TOPIC_COMMENT = "comment";

    /**
     * Kafka主题：点赞
     */
    String TOPIC_LIKE = "like";

    /**
     * Kafka主题：关注
     */
    String TOPIC_FOLLOW = "follow";

    /**
     * 系统用户ID：系统消息发起者
     */
    int SYSTEM_USER_ID = 1;
}

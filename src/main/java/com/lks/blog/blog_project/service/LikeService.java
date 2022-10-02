package com.lks.blog.blog_project.service;

import com.lks.blog.blog_project.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final RedisTemplate redisTemplate;

    @Autowired
    public LikeService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 点赞功能实现
    public void like(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);

        // 判断当前用户是否已经点过赞，即是否在redis对应的点赞set中
        Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        // 已经点过赞，再次调用此方法就会将赞取消
        if (Boolean.TRUE.equals(isMember)) {
             redisTemplate.opsForSet().remove(entityLikeKey, userId);
        } else {
            redisTemplate.opsForSet().add(entityLikeKey, userId);
        }
    }

    // 查询实体点赞的数量
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    // 查询某人对某实体的点赞状态，1表示已经点赞，0表示没有点赞
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }
}

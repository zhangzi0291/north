package com.north.base.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/25 17:19
 */
@Repository
public class RedisSessionDao extends AbstractSessionDAO {

    // Session超时时间，单位为毫秒
    private long expireTime = 30*60*1000;

    @Resource
    private RedisTemplate redisTemplate;// Redis操作类，对这个使用不熟悉的，可以参考前面的博客

    public RedisSessionDao() {
        super();
    }

    public RedisSessionDao(long expireTime, RedisTemplate redisTemplate) {
        super();
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }


    @Override // 删除session
    public void delete(Session session) {
        if (null == session) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

    @Override// 获取活跃的session，可以用来统计在线人数，如果要实现这个功能，可以在将session加入redis时指定一个session前缀，统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
    public Collection<Session> getActiveSessions() {
        return redisTemplate.keys("*");
    }

    @Override
    protected void assignSessionId(Session session, Serializable sessionId) {
        ((SimpleSession)session).setId(sessionId);
    }

    @Override// 加入session
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        ValueOperations<Serializable,Object> vo = redisTemplate.opsForValue();
        vo.set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override // 更新session
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }

        session.setTimeout(expireTime);
        ValueOperations<Serializable,Object> vo = redisTemplate.opsForValue();
        vo.set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override// 读取session
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        Session session = (Session) redisTemplate.opsForValue().get(sessionId);
        return session;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}

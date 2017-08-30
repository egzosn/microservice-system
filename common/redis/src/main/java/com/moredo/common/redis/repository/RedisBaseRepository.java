package com.moredo.common.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Redis通用操作方法类
 * @author 肖红星
 * @create 2016/11/2
 */
public abstract class RedisBaseRepository<K, V> {

    protected RedisTemplate<K, V> redisTemplate;
    private RedisAtomicLong redisAtomicLong;
    //V对应的class类型
    private Class<V> clazz;

    public RedisBaseRepository() {
        genericV();
    }

    /**
     * 解析出泛型V对应的class类
     */
    private void genericV(){
        java.lang.reflect.Type genType = this.getClass().getGenericSuperclass();
        java.lang.reflect.Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        try {
            clazz = (Class<V>) params[1];
        } catch (Exception e) {
            try {
                clazz = (Class<V>) ((ParameterizedType) params[1]).getRawType();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        //设置子类RedisTemplate对象
        complete(redisTemplate);
        this.redisTemplate = redisTemplate;
        //实例化Redis主键自增类
        redisAtomicLong = new RedisAtomicLong(clazz.getSimpleName() + ".id", redisTemplate.getConnectionFactory());
    }

    //更新子类RedisTemplate对象
    public abstract void complete(RedisTemplate redisTemplate);

    /**
     * 生成主键ID
     *
     * @return
     */
    public Long generateId() {
        return redisAtomicLong.incrementAndGet();
    }

    /**
     * 重命名对象名称
     *
     * @param oldKey 旧名称
     * @param newKey 新名称
     */
    public void rename(K oldKey, K newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 获取数据类型：如string,list,set,hash等
     *
     * @param key
     * @return
     */
    public DataType type(K key) {
        return redisTemplate.type(key);
    }

    /**
     * 删除数据
     *
     * @param key
     */
    public void delete(K key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除
     *
     * @param keys
     */
    public void delete(Collection<K> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 设置对象多久后过期，过期数据会自动删除
     *
     * @param key
     * @param time 多久后过期，是个时间长度
     * @param unit 时间长度单位：如秒，分等
     * @return
     */
    public boolean expire(K key, long time, TimeUnit unit) {
        return redisTemplate.expire(key, time, unit);
    }

    /**
     * 设置对象过期时间，到指定时间点后数据会自动删除
     *
     * @param key
     * @param date 过期时间点
     * @return
     */
    public boolean expireAt(K key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 获取对象过期剩余时间
     */
    public long getExpire(K key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 获取对象过期剩余时间：自定义设置时间单位，如秒，分等
     *
     * @param key
     * @param unit
     * @return
     */
    public long getExpire(K key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

}

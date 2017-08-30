package com.moredo.common.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis Value 操作封装类
 *
 * @author 肖红星
 * @create 2016/11/1
 */
public class RedisValueRepository<K, V> extends RedisBaseRepository<K, V> {

    private RedisTemplate<K, V> redisTemplate;

    @Override
    public void complete(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //==========================================查询===============================================

    /**
     * 获取值
     *
     * @param key
     * @return V
     */
    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量获取指定的key值
     *
     * @param keys 关键字数组
     * @return
     */
    public List<V> get(Collection<K> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 获取字符串值长度
     *
     * @param key
     * @return
     */
    public Long size(K key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 获取字符串值长度
     *
     * @param key
     * @return
     */
    public Long length(K key) {
        return size(key);
    }

    //==========================================更新===============================================

    /**
     * 更新数据
     *
     * @param key
     * @param value 更新后的值
     */
    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 更新数据，并设置数据过期时间
     *
     * @param key
     * @param value
     * @param timeout 多久后过期
     * @param unit    时间单位：如秒，分等
     */
    public void set(K key, V value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有当指定key不存在时才更新数据
     *
     * @param key
     * @param value
     */
    public boolean setIfNotExits(K key, V value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 批量更新数据
     *
     * @param map
     */
    public void set(Map<K, V> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
     *
     * @param map
     */
    public boolean setIfNotExits(Map<K, V> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 数据更新，返回更新前数据
     *
     * @param key
     * @param value 更新数据
     * @return
     */
    public V update(K key, V value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 字符串拼接
     *
     * @param key
     * @param appendValue 拼接的数据
     * @return 拼接后字符串长度
     */
    public Integer append(K key, String appendValue) {
        return redisTemplate.opsForValue().append(key, appendValue);
    }

    /**
     * 求和
     *
     * @param key
     * @param value
     * @return
     */
    public Long sum(K key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    /**
     * 求和
     *
     * @param key
     * @param value
     * @return
     */
    public double sum(K key, double value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

}

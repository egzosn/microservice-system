package com.moredo.common.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * redis Hash 操作封装类
 *
 * @author 肖红星
 * @create 2016/11/1
 */
public class RedisHashRepository<K, HK, HV> extends RedisBaseRepository {

    private RedisTemplate<K, HK> redisTemplate;

    @Override
    public void complete(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //==========================================查询===============================================

    /**
     * 获取hash里某个项的值
     *
     * @param key
     * @return V
     */
    public HV find(K key, HK itemKey) {
        return redisTemplate.<HK, HV>opsForHash().get(key, itemKey);
    }

    /**
     * 批量获取hash对象里指定的itemKeys
     * @param key
     * @param itemKeys
     * @return
     */
    public List<HV> find(K key, Collection<HK> itemKeys){
        return redisTemplate.<HK, HV>opsForHash().multiGet(key, itemKeys);
    }

    /**
     * 判断key是否在hash对象里
     * @param key
     * @param itemKey
     * @return
     */
    public boolean exists(K key, HK itemKey){
        return redisTemplate.opsForHash().hasKey(key, itemKey);
    }

    /**
     * 获取hash的item数量
     * @param key
     * @return
     */
    public long size(K key){
        return redisTemplate.opsForHash().size(key);
    }

    //==========================================更新===============================================

    /**
     * 设置hash对象里某个项的值
     * @param key
     * @param itemKey
     * @param itemValue
     */
    public void update(K key, HK itemKey, HV itemValue){
        redisTemplate.opsForHash().put(key, itemKey, itemValue);
    }

    /**
     * 批量添加hash的item项
     * @param key
     * @param map
     */
    public void update(K key, Map<HK, HV> map){
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 当hash对象指定的itemkey不存在时才设置itemvalue，否则返回false
     * @param key
     * @param itemKey
     * @param itemValue
     * @return
     */
    public boolean updateWhenNotExists(K key, HK itemKey, HV itemValue){
        return redisTemplate.opsForHash().putIfAbsent(key, itemKey, itemValue);
    }

    /**
     * hash项值求和
     * @param key
     * @param itemKey
     * @param delta
     */
    public void sum(K key, HK itemKey, long delta){
        redisTemplate.opsForHash().increment(key, itemKey, delta);
    }

    /**
     * hash项值求和
     * @param key
     * @param itemKey
     * @param delta
     */
    public void sum(K key, HK itemKey, double delta){
        redisTemplate.opsForHash().increment(key, itemKey, delta);
    }

    //==========================================删除===============================================

    /**
     * 批量删除hash中的项
     * @param key
     * @param itemKeys
     * @return 返回剩余item数量
     */
    public Long delete(K key, HK... itemKeys){
        return redisTemplate.opsForHash().delete(key, itemKeys);
    }

    /**
     * 清空列表值
     *
     * @param key
     */
    public void clear(K key) {
        super.delete(key);
    }

}

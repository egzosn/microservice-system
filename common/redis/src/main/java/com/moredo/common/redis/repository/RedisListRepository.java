package com.moredo.common.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;

/**
 * redis list 操作封装类
 *
 * @author 肖红星
 * @create 2016/11/1
 */
public class RedisListRepository<K, V> extends RedisBaseRepository {

    private RedisTemplate<K, V> redisTemplate;

    @Override
    public void complete(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //==========================================查询===============================================

    /**
     * 获取List列表
     *
     * @param key
     * @param start long，开始索引
     * @param end   long， 结束索引
     * @return List<String>
     */
    public List<V> find(K key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 通过索引获取列表中的元素
     *
     * @param key
     * @param index，索引，0表示最新的一个元素
     * @return String
     */
    public V find(K key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表长度，key为空时返回0
     *
     * @param key
     * @return Long
     */
    public Long size(K key) {
        return redisTemplate.opsForList().size(key);
    }

    //==========================================添加===============================================

    /**
     * 将一个值插入到列表头部，value可以重复，返回列表的长度
     *
     * @param key
     * @param value
     * @return 返回List的长度
     */
    public Long insert(K key, V value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将多个值插入到列表头部，value可以重复
     *
     * @param key
     * @param values
     * @return 返回List的数量size
     */
    public Long insert(K key, V... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 将一个值插入到已存在的列表头部：如果key值不存在，插入失败返回0；当成功时，返回List的长度
     *
     * @param key
     * @param value
     * @return Long
     */
    public Long addToFirstWhenExit(K key, V value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 添加一个元素到列表后面
     *
     * @param key
     * @param value
     * @return
     */
    public Long add(K key, V value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 批量添加到列表后面
     *
     * @param key
     * @param values
     * @return
     */
    public Long add(K key, V... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 批量添加到列表后面
     *
     * @param key
     * @param values
     * @return
     */
    public Long add(K key, Collection<V> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 添加到指定元素后面
     *
     * @param key
     * @param target 指定元素
     * @param value  待添加元素
     * @return
     */
    public Long insertAfter(K key, V target, V value) {
        return redisTemplate.opsForList().rightPush(key, target, value);
    }

    /**
     * 添加到指定元素前面
     *
     * @param key
     * @param target 指定元素
     * @param value  待添加元素
     * @return
     */
    public Long insertBefore(K key, V target, V value) {
        return redisTemplate.opsForList().leftPush(key, target, value);
    }

    //==========================================更新===============================================

    /**
     * 更新列表中指定的位置数据
     *
     * @param key
     * @param value
     * @param index 索引位置
     */
    public void update(K key, V value, int index) {
        redisTemplate.opsForList().set(key, index, value);
    }

    //==========================================删除===============================================

    /**
     * 移除列表第一个元素
     *
     * @param key
     * @return V
     */
    public V shift(K key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除列表最后一个均线
     *
     * @param key
     * @return
     */
    public V pop(K key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 删除指定项
     *
     * @param key
     * @param value
     * @return
     */
    public Long delete(K key, V value) {
        return redisTemplate.opsForList().remove(key, 1, value);
    }

    /**
     * 删除所有项，除指定位置项外
     *
     * @param key
     * @param begin
     * @param end
     */
    public void deleteWithOut(K key, long begin, long end) {
        redisTemplate.opsForList().trim(key, begin, end);
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

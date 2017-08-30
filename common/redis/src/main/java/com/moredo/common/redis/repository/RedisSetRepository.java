package com.moredo.common.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * redis Set 操作封装类
 *
 * @author 肖红星
 * @create 2016/11/1
 */
public class RedisSetRepository<K, V> extends RedisBaseRepository {

    private RedisTemplate<K, V> redisTemplate;

    @Override
    public void complete(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //==========================================查询===============================================

    /**
     * 获取set集合的元素数量
     * @param key
     * @return
     */
    public Long size(K key){
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含该元素
     * @param key
     * @param value
     * @return
     */
    public boolean exists(K key, V value){
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取集合数据
     * @param key
     * @return
     */
    public Set<V> find(K key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 从集合中随机获取一个元素
     * @param key
     * @return
     */
    public V random(K key){
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 从集合中随机获取多个不重复的元素
     * @param key
     * @param count 指定元素的个数
     * @return
     */
    public Set<V> randomDistinct(K key, long count){
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 从集合中随机获取多个元素，元素可能会重复出来
     * @param key
     * @param count 指定元素的个数
     * @return
     */
    public List<V> random(K key, long count){
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 获取两个集合合并后结果集
     * @param target1 集合1
     * @param target2 集合2
     * @return
     */
    public Set<V> union(K target1, K target2){
        return redisTemplate.opsForSet().union(target1, target2);
    }

    /**
     * 将多个集合与目标集合合并，返回合并后结果集
     * @param otherKeys 待合并的集合名称
     * @param target2 集合2
     * @return
     */
    public Set<V> union(Collection<K> otherKeys, K target2){
        return redisTemplate.opsForSet().union(target2, otherKeys);
    }

    /**
     * 获取两个集合不同的数据集合
     * @param target1 集合1
     * @param target2 集合2
     * @return
     */
     public Set<V> difference(K target1, K target2){
         return redisTemplate.opsForSet().difference(target1, target2);
     }

    /**
     * 获取多个集合不同的数据集合
     * @param otherKeys 其他集合
     * @param target 目标集合
     * @return
     */
    public Set<V> difference(Collection<K> otherKeys, K target){
        return redisTemplate.opsForSet().difference(target, otherKeys);
    }

    //==========================================添加===============================================

    /**
     * 往集合里添加新的元素
     * @param key
     * @param values
     * @return
     */
    public Long add(K key, V... values){
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 保存合并两个集合到新的集合
     * @param newKey 新的集合名称
     * @param target1 集合1名称
     * @param target2 信鸽2名称
     */
    public Long union(K newKey, K target1, K target2){
        return redisTemplate.opsForSet().unionAndStore(newKey , target1, target2);
    }

    /**
     * 保存多个集合与目标集合合并
     * @param newKey 新的集合名称
     * @param otherKeys 待合并集合名称
     * @param target 待合并到的集合名称
     * @return
     */
    public Long union(K newKey, Collection<K> otherKeys, K target){
        return redisTemplate.opsForSet().unionAndStore(newKey , otherKeys, target);
    }

    /**
     * 保存两个集合不同的数据集合
     * @param newKey 新的集合名称
     * @param target1 集合1
     * @param target2 集合2
     * @return
     */
    public Long difference(K newKey, K target1, K target2){
        return redisTemplate.opsForSet().differenceAndStore(newKey, target1, target2);
    }

    /**
     * 保存多个集合不同的数据集合
     * @param newKey 新的集合名称
     * @param otherKeys 其他集合
     * @param target 目标集合
     * @return
     */
    public Long difference(K newKey, Collection<K> otherKeys, K target){
        return redisTemplate.opsForSet().differenceAndStore(newKey, otherKeys, target);
    }

    //==========================================删除===============================================

    /**
     * 删除set集合的第一个元素
     * @param key
     * @return
     */
    public V pop(K key){
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 批量删除
     * @param key
     * @param items
     * @return
     */
    public Long delete(K key, V... items){
        return redisTemplate.opsForSet().remove(key, items);
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

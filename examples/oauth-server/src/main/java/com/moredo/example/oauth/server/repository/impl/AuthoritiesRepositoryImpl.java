package com.moredo.example.oauth.server.repository.impl;

import com.moredo.example.oauth.server.entity.Authorities;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * 用户持久化类
 *
 * @author 肖红星
 * @create 2016/11/9
 */
public class AuthoritiesRepositoryImpl {

    @Autowired
    private EntityManager entityManager;

    public List<Authorities> findByUsername(String username) {
        Query query = entityManager.createNativeQuery("select * from authorities where username = ?1", Authorities.class);
        query.setParameter(1, username);
        return query.getResultList();
    }

}

package com.moredo.example.oauth.server.repository;

import com.moredo.example.oauth.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 用户持久化类
 *
 * @author 肖红星
 * @create 2016/11/9
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    User findByUserId(String userId);

}

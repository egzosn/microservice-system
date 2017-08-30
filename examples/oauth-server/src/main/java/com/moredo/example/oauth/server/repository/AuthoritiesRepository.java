package com.moredo.example.oauth.server.repository;

import com.moredo.example.oauth.server.entity.Authorities;
import com.moredo.example.oauth.server.entity.AuthoritiesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户持久化类
 *
 * @author 肖红星
 * @create 2016/11/9
 */
@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesPK>, JpaSpecificationExecutor<Authorities> {

    List<Authorities> findByUsername(String username);

}

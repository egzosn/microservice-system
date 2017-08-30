package com.moredo.example.oauth.server.service;

import com.moredo.example.oauth.server.entity.Authorities;
import com.moredo.example.oauth.server.entity.User;
import com.moredo.example.oauth.server.repository.AuthoritiesRepository;
import com.moredo.example.oauth.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User account = userRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }
        String password = account.getPassword();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(getAuthorities(account.getUsername()));
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(account.getUserId(), password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        return user;
    }

    /**
     * 根据用户名所有权限
     *
     * @param username
     * @return
     */
    private String getAuthorities(String username) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Authorities> authorities = authoritiesRepository.findByUsername(username);
        for (Authorities temp : authorities) {
            if (temp == null) {
                continue;
            }
            stringBuilder.append(temp.getAuthoritiesPK().getAuthority()).append(",");
        }
        return stringBuilder.toString();
    }

    public User loadUserByUserId(String userId){
        return userRepository.findByUserId(userId);
    }

}

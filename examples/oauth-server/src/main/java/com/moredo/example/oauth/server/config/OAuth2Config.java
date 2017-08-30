//package com.moredo.example.oauth.server.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
//
//import javax.sql.DataSource;
//import java.security.KeyPair;
//
//@EnableAuthorizationServer
//public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    private DataSource dataSource;
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    public TokenStore tokenStore;
//    @Autowired
//    public JwtAccessTokenConverter jwtAccessTokenConverter;
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(this.authenticationManager).tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
////        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
//        oauthServer.allowFormAuthenticationForClients();
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource);
//    }
//
////    @Bean
////    public TokenStore tokenStore() {
//////        return new RedisTokenStore(redisConnectionFactory);
//////        return new JdbcTokenStore(dataSource);
////        return new JwtTokenStore(jwtAccessTokenConverter());
////    }
////
////    @Bean
////    public JwtAccessTokenConverter jwtAccessTokenConverter() {
////        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "mySecretKey".toCharArray());
////        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
////        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
////        return converter;
////    }
//
//}

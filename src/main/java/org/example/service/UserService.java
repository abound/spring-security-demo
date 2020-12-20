package org.example.service;

import org.example.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: MaiYu
 * @Date: Create in 11:09 2020/12/20
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    Map<String,Object> login(String username, String password);

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    String register(User user);

    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    String refreshToken(String oldToken);

     Collection<? extends GrantedAuthority> getAuthorities(int userId);
}

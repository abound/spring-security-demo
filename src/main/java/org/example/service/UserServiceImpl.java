package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.JwtTokenUtil;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: MaiYu
 * @Date: Create in 10:52 2020/12/20
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public Map<String,Object> login(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, password);

        SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info(token);
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
        log.info(usernameFromToken);
        String code="success";
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("code",code);
        map.put("name",usernameFromToken);

        return map;
    }

    @Override
    public String register(User user) {
        String username = user.getUsername();
        if (userMapper.selectByName(username) != null) {
            return "用户已存在";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        user.setType(1);
        userMapper.insertUser(user);
        return "success";
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());
        if (!jwtTokenUtil.isTokenExpired(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return "error";
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(int userId){
        User user = userMapper.selectById(userId);

        List<GrantedAuthority> list=new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch (user.getType()) {
                    case 1:
                        return "admin";
                    case 2:
                        return "user";
                    default:
                        return "model";
                }
            }
        });
        return list;
    }
}

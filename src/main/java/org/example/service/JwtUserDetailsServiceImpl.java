package org.example.service;

import org.example.entity.JwtUser;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: MaiYu
 * @Date: Create in 10:47 2020/12/20
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {


    @Resource
    UserMapper userMapper;

    @Resource
    UserService userServiceImpl;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userMapper.selectByName(s);

        if (user==null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
        }else {
            return new JwtUser(user.getUsername(), user.getPassword(), userServiceImpl.getAuthorities(user.getId()));
        }


    }
}

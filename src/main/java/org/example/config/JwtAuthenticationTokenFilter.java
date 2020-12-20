package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


/**
 * @Author: MaiYu
 * @Date: Create in 11:03 2020/12/20
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("已调用");
        String authHeader = request.getHeader("Authorization");
        //startsWith() 方法用于检测字符串是否以指定的前缀开始。
        log.info(authHeader);


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring("Bearer ".length());// The part after "Bearer "
            log.info(authToken);
            //从token获取用户名
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            log.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                //验证Token是否有效
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    //userDetails.getAuthorities() 给用户配置相应权限
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user:{}", username);
                    //即存储认证授权的相关信息，实际上就是存储"当前用户"账号信息和相关权限
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
    static JwtTokenUtil al=new JwtTokenUtil();
    public static void main(String[] args) {
        String username = al.getUsernameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MTEwMzQ3NTUsInN1YiI6bnVsbCwiY3JlYXRlZCI6MTYwODQ0Mjc1NTgxM30._FQHhJROzE8YTiJU0TtouTcuuT4PCF0zJywKhMDb-_9XChfoCMIB-WZ_WDXDf46DnAdztaRM1iXI4WqFmYLRCA");
        System.out.println(username);
    }
}

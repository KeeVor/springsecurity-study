package com.keevor.filter;

import cn.hutool.core.util.ObjectUtil;
import com.keevor.domain.LoginUser;
import com.keevor.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在springSecurity过滤器之前执行，会过滤器所有请求。
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 配置jwt校验器
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //1.从请求头中获取token信息
        String token = httpServletRequest.getHeader("token");
        //2.判断token是否为空
        if (ObjectUtil.hasNull(token)){
            //未登录状态，放行，让springSecurity拦截。
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //3.解析token
        String userId;
        try {
            Claims claims = JwtUtil.getAllClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            //token不合法，继续放行。
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //4.从redis中获取用户信息
        LoginUser user = (LoginUser) redisTemplate.opsForValue().get("user:id:" + userId);
        if (user == null){
            //用户登录时效过期，放行。
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //5.封装用户信息
        //这里选择三个参数的构造方法，表示用户通过验证。则不会被springSecurity再次认证。
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        //6.把用户信息放到SecurityContextHolder，后续过滤器则通过这里获取到认证状态。
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //7.放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}

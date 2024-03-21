package com.keevor.service.impl;

import cn.hutool.json.JSONUtil;
import com.keevor.domain.LoginUser;
import com.keevor.domain.R;
import com.keevor.domain.User;
import com.keevor.service.LoginService;
import com.keevor.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 处理登录逻辑
     * @param user
     * @return
     */
    @Override
    public R<String> login(User user) {
        //把用户信息封装成Authentication来进行验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),user.getPassword()   //使用UsernamePasswordAuthenticationToken实现类
        );
        //传入校验对象
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate == null){
            //认证失败
            throw new RuntimeException("用户名或密码错误！");
        }
        //获取用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //用户信息放入redis
        redisTemplate.opsForValue().set("user:id:" + loginUser.getUser().getId(),loginUser);
        //生成token
        String token = JwtUtil.generateToken(loginUser.getUser().getId().toString(), 100000000);
        //拼接成json
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        String json = JSONUtil.toJsonStr(map);
        return R.success(json);
    }

    /**
     * 退出登录
     * @return
     */
    public R<String> logout() {
        //1.获取当前用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //2.获取用户id
        Integer userId = loginUser.getUser().getId();
        //3.从redis删除用户信息
        redisTemplate.delete("user:id:" + userId);
        return R.success("退出登录成功!");
    }
}

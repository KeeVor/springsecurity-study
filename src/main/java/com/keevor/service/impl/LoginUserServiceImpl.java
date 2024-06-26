package com.keevor.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.keevor.common.CustomException;
import com.keevor.domain.LoginUser;
import com.keevor.domain.User;
import com.keevor.mapper.UserMapper;
import com.keevor.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LoginUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    /**
     * 根据数据库去查询用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中查询用户
        //构造查询条件
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername,username);
        User user = userMapper.selectOne(lqw);
        if (user == null){
            //用户不存在
            log.info("用户认证失败，尝试认证用户为:{}",username);
            throw new CustomException("用户名不存在！");
        }
        //从数据库中查询权限信息
        List<String> roles = roleService.getRoleByUserId(user.getId());
        return new LoginUser(user, roles);
    }
}

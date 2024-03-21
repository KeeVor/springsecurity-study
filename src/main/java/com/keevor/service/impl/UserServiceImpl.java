package com.keevor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keevor.domain.User;
import com.keevor.service.UserService;
import com.keevor.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author KeeVor
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-03-19 20:49:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





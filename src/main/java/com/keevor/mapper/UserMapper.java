package com.keevor.mapper;

import com.keevor.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
* @author KeeVor
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-03-19 20:49:09
* @Entity com.keevor.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}





package com.keevor;


import com.keevor.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SpringbootTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Test
    public void test01(){
        //获取123456的加密密码
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
        // $2a$10$LNPyNIckLTy2WclbZcf2Wex3k5wlgPZLqN3mGzX/FiSH4NaVcuT/y
    }

    @Test
    public void test02(){
        //测试redis功能
        User user = new User();
        user.setId(1);
        user.setAge(20);
        user.setUsername("zhangsan");
        user.setPassword("123");
        redisTemplate.opsForValue().set("user:"+user.getId(),user);
    }
}

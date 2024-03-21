package com.keevor.controller;


import com.keevor.domain.R;
import com.keevor.domain.User;
import com.keevor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;
    /**
     * 自定义登录接口
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody User user){
        return loginService.login(user);
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public R<String> logout(){
        return loginService.logout();
    }

}

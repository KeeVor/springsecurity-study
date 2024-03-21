package com.keevor.controller;


import com.keevor.common.CustomException;
import com.keevor.domain.LoginUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/{e}")
    public String hello(@PathVariable String e){
        if ("1".equals(e)){
            throw new CustomException("异常测试！");
        }
        //获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return "你好，" + loginUser.getUser().getUsername() ;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('user')")
    public String user(){
        return "只有user权限才能访问!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public String admin(){
        return "只有admin权限才能访问!";
    }

    @GetMapping("/root")
    @PreAuthorize("hasAuthority('root')")
    public String root(){
        return "只有root权限才能访问!";
    }

}

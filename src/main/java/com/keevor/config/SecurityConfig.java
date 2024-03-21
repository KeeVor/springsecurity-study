package com.keevor.config;

import com.keevor.filter.JWTFilter;
import com.keevor.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //注入jwt过滤器
    @Autowired
    private JWTFilter jwtFilter;

    //配置未认证状态返回信息
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    //配置密码加密器
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //暴露登录校验器
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //配置security

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置HTTP安全性规则
        http
                .csrf().disable()//关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //关闭session功能
                .and()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint) //配置未认证状态信息格式
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)//配置jwt校验过滤器
                .authorizeRequests()
                .antMatchers("/user/login").anonymous()// 登录请求放行
                .anyRequest().authenticated(); // 其他所有URL都需要进行身份验证


    }
}

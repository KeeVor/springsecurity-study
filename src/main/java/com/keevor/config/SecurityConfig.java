package com.keevor.config;

import com.keevor.filter.JWTFilter;
import com.keevor.handler.CustomAccessDeniedHandler;
import com.keevor.handler.CustomAuthenticationEntryPointHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启授权功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //注入jwt过滤器
    @Autowired
    private JWTFilter jwtFilter;

    //配置自定义认证异常处理器
    @Autowired
    private CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;

    //配置自定义鉴权异常处理器
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

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
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)//配置jwt校验过滤器
                .authorizeRequests()
                .antMatchers("/user/login").anonymous()// 登录请求放行
                .anyRequest().authenticated(); // 其他所有URL都需要进行身份验证

        http
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPointHandler)//配置自定义认证异常处理器
                .accessDeniedHandler(customAccessDeniedHandler);//配置自定义鉴权异常处理器



    }
}

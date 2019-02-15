package com.yanqiancloud.control.sysmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @ProjectName: cloud-base
 * @Description: 安全访问配置
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/23 14:34
 * @Version: 1.0.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler customeAuthenticationSuccessHandler;

    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Autowired
    private CustomerFilterSecurityInterceptorConfig customerFilterSecurityInterceptorConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //配置jwt验证过滤器
        http.apply(jwtAuthenticationSecurityConfig);
        //配置安全拦截器
        http.apply(customerFilterSecurityInterceptorConfig);

        http.formLogin()
                .successHandler(customeAuthenticationSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                // 禁用 Spring Security 自带的跨域处理
                .disable();

    }
}

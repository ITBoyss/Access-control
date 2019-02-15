package com.yanqiancloud.control.sysmanager.config;

import com.yanqiancloud.control.sysmanager.security.JwtAuthenticationProvider;
import com.yanqiancloud.control.sysmanager.filter.JwtFilter;
import com.yanqiancloud.control.sysmanager.login.jwtutil.JwtHelper;
import com.yanqiancloud.control.sysmanager.login.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cloud-base
 * @Description: JwtFilter配置
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/27 13:48
 * @Version: 1.0.0
 */
@Component
public class JwtAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    private SysUserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        //配置Filter
        JwtFilter jwtAuthenticationFilter = new JwtFilter();
        //设置在jwt验证通过后继续执行后续过滤器链，而不是停止执行，直接转向成功处理器
        jwtAuthenticationFilter.setContinueChainBeforeSuccessfulAuthentication(true);
        //配置AuthenticationManager
        jwtAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        //配置
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();
        //配置jwtAuthenticationProvider所调用的UserDetailsService,jwtHelp
        jwtAuthenticationProvider.setUserDetailsService(userDetailsService);
        jwtAuthenticationProvider.setJwtHelper(jwtHelper);
        //将自定义的AuthenticationProvider添加到AuthenticationManager所管理的Provider集合里面去
        builder.authenticationProvider(jwtAuthenticationProvider)
                //将过滤器添加到用户名密码验证过滤器的后面就行
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

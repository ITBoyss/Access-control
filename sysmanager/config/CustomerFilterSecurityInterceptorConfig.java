package com.yanqiancloud.control.sysmanager.config;

import com.yanqiancloud.control.sysmanager.security.CustomerFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cloud-base
 * @Description: CustomerFilterSecurityInterceptorConfig相关属性配置
 * @Author: WeiLingYun
 * @CreateDate: 2018/12/5 21:18
 * @Version: 1.0.0
 */
@Component
public class CustomerFilterSecurityInterceptorConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AccessDecisionManager customerAccessDecisionManager;

    @Autowired
    private FilterInvocationSecurityMetadataSource customerInvocationSecurityMetadataSource;

    @Override
    public void configure(HttpSecurity builder) throws Exception {

        CustomerFilterSecurityInterceptor customerFilterSecurityInterceptor = new CustomerFilterSecurityInterceptor();
        customerFilterSecurityInterceptor.setFilterSecurityMetadataSource(customerInvocationSecurityMetadataSource);
        customerFilterSecurityInterceptor.setAccessDecisionManager(customerAccessDecisionManager);
        customerFilterSecurityInterceptor.setRejectPublicInvocations(true);
        builder.addFilterBefore(customerFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
}

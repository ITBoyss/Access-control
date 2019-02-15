package com.yanqiancloud.control.sysmanager.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ProjectName: cloud-base
 * @Description: 启用jwt配置
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/26 9:12
 * @Version: 1.0.0
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtPropertiesConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.yanqiancloud.control.sysmanager.login.handle;

import com.yanqiancloud.control.sysmanager.config.JwtProperties;
import com.yanqiancloud.control.sysmanager.login.jwtutil.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @ProjectName: cloud-base
 * @Description: 自定义登陆成功处理器，返回客户端jwt
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/25 17:36
 * @Version: 1.0.0
 */
@Component("customeAuthenticationSuccessHandler")
public class CustomeAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 存放所有需要校验验证码的url
     */
    private Set<String> urlSet = new HashSet<>();

    /**
     * Spring验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String jwtToken = jwtHelper.generateJWT(authentication);
        response.addHeader(jwtProperties.TOKEN_HEADER, jwtToken);
    }

    /**
     * @description 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        addUrlToSet(jwtProperties.getUrl());
    }

    /**
     * @param urlString
     * @description 将系统中配置的需要校验验证码的URL根据校验的类型放入set
     */
    protected void addUrlToSet(String urlString) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(urlString)) {
            String[] urls = org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlSet.add(url);
            }
        }
    }
}

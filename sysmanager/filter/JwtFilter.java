package com.yanqiancloud.control.sysmanager.filter;

import com.yanqiancloud.control.sysmanager.security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ProjectName: cloud-base
 * @Description: 拦截请求，验证jwt
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/26 19:36
 * @Version: 1.0.0
 */
public class JwtFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_AUTHORIZATION_KEY = "Authorization";

    private String authorizationParameter = SPRING_SECURITY_FORM_AUTHORIZATION_KEY;

    private static final String SPRING_SECURITY_JWT_FILTER_PATH = "/sys/**";

    private static final String POST_METHOD = "POST";

    private boolean postOnly = false;

    public JwtFilter() {
        super(new AntPathRequestMatcher(SPRING_SECURITY_JWT_FILTER_PATH));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !POST_METHOD.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String token = obtainToken(request);

        if (token == null) {
            token = "";
        }
        token = token.trim();

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(token);

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    public void setContinueChainBeforeSuccessfulAuthentication(boolean continueChainBeforeSuccessfulAuthentication) {
        super.setContinueChainBeforeSuccessfulAuthentication(continueChainBeforeSuccessfulAuthentication);
    }

    private String obtainToken(HttpServletRequest request) {
        return request.getHeader(authorizationParameter);
    }

    protected void setDetails(HttpServletRequest request, JwtAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setAuthorizationParameter(String authorizationParameter) {
        Assert.hasText(authorizationParameter, "Authorization parameter must not be empty or null");
        this.authorizationParameter = authorizationParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}

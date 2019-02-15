package com.yanqiancloud.control.sysmanager.security;

import com.yanqiancloud.control.sysmanager.login.jwtutil.JwtHelper;
import com.yanqiancloud.control.sysmanager.security.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * @ProjectName: cloud-base
 * @Description: JwtAuthenticationProvider jwt验证逻辑
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/27 9:58
 * @Version: 1.0.0
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Log logger = LogFactory.getLog(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String USER_CODE = "userCode";

    private String userCode = USER_CODE;

    private String tokenPrefix = TOKEN_PREFIX;

    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();

    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();

    private UserDetailsService userDetailsService;

    private JwtHelper jwtHelper;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(JwtAuthenticationToken.class, authentication, messages.getMessage("JwtAuthenticationProvider.onlySupports", "Only JwtAuthenticationToken is supported"));
        Claims claims = verifyToken(authentication);
        UserDetails user = retrieveUser(claims);
        Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        preAuthenticationChecks.check(user);
        postAuthenticationChecks.check(user);
        JwtAuthenticationToken result = new JwtAuthenticationToken(authentication.getName(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    /**
     * @param authentication
     * @return
     * @description 验证token
     */
    private Claims verifyToken(Authentication authentication) {
        String token = authentication.getName();
        if (!StringUtils.isNotBlank(token)) {
            throw new JwtException("token is null");
        }
        if (!StringUtils.startsWithIgnoreCase(token, tokenPrefix)) {
            throw new JwtException("illegal token");
        }
        Claims claims = jwtHelper.parseJwt(token.substring(7));
        if (claims == null) {
            throw new JwtException("token parse exception");
        }
        Long expire = claims.getExpiration().getTime();
        Long current = System.currentTimeMillis();
        if (expire <= current) {
            throw new JwtException("token is expired");
        }
        return claims;
    }

    /**
     * @param claims
     * @return
     * @throws AuthenticationException
     * @description 检索用户
     */
    protected final UserDetails retrieveUser(Claims claims) throws AuthenticationException {
        String code = String.valueOf(claims.get(userCode));
        if (!StringUtils.isNotBlank(code)) {
            throw new JwtException("obtain user failure from jwt");
        }
        try {
            UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(code);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        //判断传进来的东西是不是JwtAuthenticationToken这种类型的
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 验证用户账户合法性
     */
    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                logger.debug("User account is locked");
                throw new LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            }
            if (!user.isEnabled()) {
                logger.debug("User account is disabled");
                throw new DisabledException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            }
            if (!user.isAccountNonExpired()) {
                logger.debug("User account is expired");
                throw new AccountExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }

    /**
     * 验证用户密码合法性
     */
    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                logger.debug("User account credentials have expired");
                throw new CredentialsExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
            }
        }
    }

    public void setJwtHelper(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}

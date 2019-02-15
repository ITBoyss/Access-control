package com.yanqiancloud.control.sysmanager.security;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @ProjectName: cloud-base
 * @Description: 权限验证的实现
 * @Author: WeiLingYun
 * @CreateDate: 2018/12/5 19:46
 * @Version: 1.0.0
 */
@Component("customerAccessDecisionManager")
public class CustomerAccessDecisionManager implements AccessDecisionManager {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (CollectionUtils.isEmpty(configAttributes)) {
            return;
        }
        //权限判断
        for (ConfigAttribute configAttribute : configAttributes) {
            String urlAcl = configAttribute.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if ("ROLE_".concat(urlAcl).equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

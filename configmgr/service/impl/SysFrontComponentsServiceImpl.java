package com.yanqiancloud.control.configmgr.service.impl;

import com.yanqiancloud.control.configmgr.domain.SysFrontComponents;
import com.yanqiancloud.control.configmgr.mapper.SysFrontComponentsMapper;
import com.yanqiancloud.control.configmgr.service.ISysFrontComponentsService;
import com.yanqiancloud.mybatis.service.BaseServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @ProjectName: cloud-base
 * @Description:
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/28 22:27
 * @Version: 1.0.0
 */
@Service
public class SysFrontComponentsServiceImpl extends BaseServiceImpl<SysFrontComponents> implements ISysFrontComponentsService {

    protected final Log logger = LogFactory.getLog(getClass());

    private boolean alwaysReauthenticate = false;

    private AuthenticationManager authenticationManager = new NoOpAuthenticationManager();

    @Autowired
    private SysFrontComponentsMapper componentsMapper;



    @Override
    public List<SysFrontComponents> selectCurrentUserAllComponents() {
        //获取当前用户所拥有的权限(如果未登录或者没有给该用户绑定角色，这里获取的就是anomyousUser)
        Authentication authenticated = this.authenticateIfRequired();
        Collection<? extends GrantedAuthority> authorities = authenticated.getAuthorities();
        Set<String> roleCodes = AuthorityUtils.authorityListToSet(authorities);
        List<String> roleCodeList = new ArrayList<>();
        roleCodes.forEach(roleCode -> roleCodeList.add(roleCode.substring(5)));
        List<SysFrontComponents> components = componentsMapper.selectCurrentUserAllComponents(roleCodeList);
        List<SysFrontComponents> resultList = new ArrayList<>();
        RecursionResultTree(components, resultList);
        return resultList;
    }

    private void RecursionResultTree(List<SysFrontComponents> components, List<SysFrontComponents> resultList) {
        Long first = 0L;
        for (SysFrontComponents temp : components) {
            if (first.equals(temp.getParentComId())) {
                RecursionProcess(components, temp);
                resultList.add(temp);
            }
        }
    }

    private void RecursionProcess(List<SysFrontComponents> components, SysFrontComponents component) {
        List<SysFrontComponents> childList = new ArrayList<>();
        for (SysFrontComponents temp : components) {
            if (temp.getParentComId().equals(component.getComId())) {
                childList.add(temp);
                RecursionProcess(components, temp);
            }
        }
        component.setChildList(childList);
    }

    private Authentication authenticateIfRequired() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !alwaysReauthenticate) {
            if (logger.isDebugEnabled()) {
                logger.debug("Previously Authenticated: " + authentication);
            }
            return authentication;
        }

        authentication = authenticationManager.authenticate(authentication);

        // We don't authenticated.setAuthentication(true), because each provider should do
        // that
        if (logger.isDebugEnabled()) {
            logger.debug("Successfully Authenticated: " + authentication);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    private static class NoOpAuthenticationManager implements AuthenticationManager {
        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            throw new AuthenticationServiceException("Cannot authenticate "
                    + authentication);
        }
    }

    public boolean isAlwaysReauthenticate() {
        return alwaysReauthenticate;
    }

    /**
     * Indicates whether the <code>AbstractSecurityInterceptor</code> should ignore the
     * {@link Authentication#isAuthenticated()} property. Defaults to <code>false</code>,
     * meaning by default the <code>Authentication.isAuthenticated()</code> property is
     * trusted and re-authentication will not occur if the principal has already been
     * authenticated.
     *
     * @param alwaysReauthenticate <code>true</code> to force
     *                             <code>AbstractSecurityInterceptor</code> to disregard the value of
     *                             <code>Authentication.isAuthenticated()</code> and always re-authenticate the
     *                             request (defaults to <code>false</code>).
     */
    public void setAlwaysReauthenticate(boolean alwaysReauthenticate) {
        this.alwaysReauthenticate = alwaysReauthenticate;
    }
}

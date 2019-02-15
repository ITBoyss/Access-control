package com.yanqiancloud.control.sysmanager.security;

import com.yanqiancloud.control.configmgr.domain.SysApiResources;
import com.yanqiancloud.control.configmgr.domain.SysFrontComponents;
import com.yanqiancloud.control.configmgr.domain.SysResComMap;
import com.yanqiancloud.control.configmgr.domain.SysRoleComMap;
import com.yanqiancloud.control.configmgr.domain.SysRoles;
import com.yanqiancloud.control.configmgr.mapper.SysApiResourcesMapper;
import com.yanqiancloud.control.configmgr.mapper.SysFrontComponentsMapper;
import com.yanqiancloud.control.configmgr.mapper.SysResComMapMapper;
import com.yanqiancloud.control.configmgr.mapper.SysRoleComMapMapper;
import com.yanqiancloud.control.configmgr.mapper.SysRolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: cloud-base
 * @Description: 用于加载url权限
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/28 16:40
 * @Version: 1.0.0
 */
@Component("customerInvocationSecurityMetadataSource")
public class CustomerInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    @Autowired
    private SysApiResourcesMapper apiResourcesMapper;

    @Autowired
    private SysResComMapMapper resComMapMapper;

    @Autowired
    private SysFrontComponentsMapper frontComponentsMapper;

    @Autowired
    private SysRoleComMapMapper roleComMapMapper;

    @Autowired
    private SysRolesMapper rolesMapper;

    /**
     * 加载权限表中所有权限
     */
    private void loadResourceConfig() {
        List<SysApiResources> resources = apiResourcesMapper.selectAll();
        List<SysResComMap> resComMaps = resComMapMapper.selectAll();
        List<SysFrontComponents> components = frontComponentsMapper.selectAll();
        List<SysRoleComMap> roleComMaps = roleComMapMapper.selectAll();
        List<SysRoles> roles = rolesMapper.selectAll();

        Map<SysApiResources, Collection<Long>> rescommap = new HashMap<>(16);
        resources.forEach(resource -> {
            Set<Long> set = new HashSet<>(8, 0.75f);
            resComMaps.forEach(mapping -> {
                if (resource.getResId().equals(mapping.getRescId())) {
                    set.add(mapping.getComId());
                }
            });
            rescommap.put(resource, set);
        });

        Map<Long, Collection<SysRoles>> comrolemap = new HashMap<>(16);
        components.forEach(component -> {
            Set<SysRoles> set = new HashSet<>(8, 0.75f);
            roleComMaps.forEach(mapping -> {
                if (component.getComId().equals(mapping.getComId())) {
                    roles.forEach(role -> {
                        if (role.getRoleId().equals(mapping.getRoleId())) {
                            set.add(role);
                        }
                    });
                }
            });
            comrolemap.put(component.getComId(), set);
        });

        //得到资源和权限角色的关系
        Map<SysApiResources, Collection<SysRoles>> resrolemap = new HashMap<>(16);
        rescommap.forEach((resource, componentIdList) -> {
            Set<SysRoles> set = new HashSet<>(8, 0.75f);
            comrolemap.forEach((componentId, roleList) -> {
                if (componentIdList.contains(componentId)) {
                    set.addAll(roleList);
                }
            });
            resrolemap.put(resource, set);
        });

        resrolemap.forEach((resource, roleList) -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            roleList.forEach(role -> {
                ConfigAttribute configAttribute = new SecurityConfig(role.getRoleCode());
                configAttributes.add(configAttribute);
            });
            map.put(resource.getResPath(), configAttributes);
        });
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceConfig();
        }
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        if (map == null) {
            return null;
        }
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
            String requestUrl = entry.getKey();
            AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(requestUrl);
            if (antPathRequestMatcher.matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}

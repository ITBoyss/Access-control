package com.yanqiancloud.control.sysmanager.login.service;

import com.yanqiancloud.control.configmgr.domain.SysRoles;
import com.yanqiancloud.control.configmgr.domain.SysUsers;
import com.yanqiancloud.control.configmgr.mapper.SysRolesMapper;
import com.yanqiancloud.control.configmgr.mapper.SysUsersMapper;
import com.yanqiancloud.control.sysmanager.login.domian.SysUserDetails;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ProjectName: cloud-base
 * @Description: 根据用户登录名验证用户信息
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/23 15:49
 * @Version: 1.0.0
 */
@Component
public class SysUserDetailsService implements UserDetailsService {

    public static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private SysUsersMapper usersMapper;

    @Autowired
    private SysRolesMapper rolesMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        //根据登陆用户用户编码查询用户信息
        SysUsers user = usersMapper.findByUserCode(userCode);
        String userName = user.getUserName();
        String password = user.getPassword();

        String email = user.getEmail();
        String phone = user.getPhone();

        List<GrantedAuthority> authorities = getGrantedAuthorities(user);

        SysUserDetails sysUserDetails = new SysUserDetails(userName, passwordEncoder.encode(password), email, phone,
                true, userCode, true, true, true, authorities);
        return sysUserDetails;
    }

    private List<GrantedAuthority> getGrantedAuthorities(SysUsers user) {
        List<SysRoles> roles = rolesMapper.findByUserId(user.getUserId());
        List<GrantedAuthority> authorities;
        if (CollectionUtils.isEmpty(roles)) {
            authorities = AuthorityUtils.NO_AUTHORITIES;
        } else {
            Set<String> roleCodes = new HashSet<>();
            roles.forEach(role -> roleCodes.add(ROLE_PREFIX + role.getRoleCode()));
            String authorityString = StringUtils.join(roleCodes, ",");
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
        }
        return authorities;
    }
}

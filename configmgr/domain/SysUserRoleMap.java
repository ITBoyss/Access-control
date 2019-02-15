package com.yanqiancloud.control.configmgr.domain;

import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ModifyAudit
@VersionAudit
@Table(name = "sys_user_role_map")
public class SysUserRoleMap {

    @Id
    @GeneratedValue
    private Long urMapId;
    private Long userId;
    private Long roleId;


    public Long getUrMapId() {
        return urMapId;
    }

    public void setUrMapId(Long urMapId) {
        this.urMapId = urMapId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}

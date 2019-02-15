package com.yanqiancloud.control.configmgr.domain;

import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ModifyAudit
@VersionAudit
@Table(name = "sys_role_com_map")
public class SysRoleComMap {

    @Id
    @GeneratedValue
    private long rolecMapId;

    private long roleId;

    private String roleCode;

    private Long comId;


    public long getRolecMapId() {
        return rolecMapId;
    }

    public void setRolecMapId(long rolecMapId) {
        this.rolecMapId = rolecMapId;
    }


    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }


    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }
}

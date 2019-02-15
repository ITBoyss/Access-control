package com.yanqiancloud.control.configmgr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;
import com.yanqiancloud.mybatis.domain.AuditDomain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@ModifyAudit
@VersionAudit
@Table(name = "sys_users")
public class SysUsers extends AuditDomain {

    @Id
    @GeneratedValue
    private Long userId;

    private String userCode;

    private String userName;

    @JsonIgnore
    private String password;

    private String email;

    private String phone;

    private Boolean enableFlag;

    private Boolean deleteFlag;

    @Transient
    @JsonIgnore
    private Set<SysRoles> roles;

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Set<SysRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRoles> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

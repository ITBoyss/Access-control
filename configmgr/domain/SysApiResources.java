package com.yanqiancloud.control.configmgr.domain;


import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;
import com.yanqiancloud.mybatis.domain.AuditDomain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@ModifyAudit
@VersionAudit
@Table(name = "sys_api_resources")
public class SysApiResources extends AuditDomain {

    @Id
    @GeneratedValue
    private Long resId;

    private String resCode;

    private String resPath;

    private String resMethod;

    private String resLevel;

    private String access;

    private String resServiceName;

    private String description;

    @Transient
    private List<Long> comIds;

    public List<Long> getComIds() {
        return comIds;
    }

    public void setComIds(List<Long> comIds) {
        this.comIds = comIds;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }


    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }


    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }


    public String getResMethod() {
        return resMethod;
    }

    public void setResMethod(String resMethod) {
        this.resMethod = resMethod;
    }


    public String getResLevel() {
        return resLevel;
    }

    public void setResLevel(String resLevel) {
        this.resLevel = resLevel;
    }


    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }


    public String getResServiceName() {
        return resServiceName;
    }

    public void setResServiceName(String resServiceName) {
        this.resServiceName = resServiceName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

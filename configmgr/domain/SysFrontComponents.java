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
@Table(name = "sys_front_components")
public class SysFrontComponents extends AuditDomain {

    @Id
    @GeneratedValue
    private Long comId;

    private String comCode;

    private String comType;

    private Long parentComId;

    private String description;

    @Transient
    private List<SysFrontComponents> childList;


    public List<SysFrontComponents> getChildList() {
        return childList;
    }

    public void setChildList(List<SysFrontComponents> childList) {
        this.childList = childList;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }


    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }


    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }


    public Long getParentComId() {
        return parentComId;
    }

    public void setParentComId(Long parentComId) {
        this.parentComId = parentComId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

package com.yanqiancloud.control.configmgr.domain;

import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/22
 */
@ModifyAudit
@VersionAudit
@Table(name = "mgr_applications")
public class MgrApplications {

    @Id
    @GeneratedValue
    private Long appId;
    private String appName;
    private String appDescription;
    private String appType;


    public Long getAppId() {
        return appId;
    }

    public void setAppID(Long appId) {
        this.appId = appId;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}

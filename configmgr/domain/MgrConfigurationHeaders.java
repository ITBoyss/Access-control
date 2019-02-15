package com.yanqiancloud.control.configmgr.domain;


import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ModifyAudit
@VersionAudit
@Table(name = "mgr_configuration_headers")
public class MgrConfigurationHeaders {

  @Id
  @GeneratedValue
  private long configHeaderId;
  private String configNumber;
  private String configStatus;
  private long envId;
  private long appId;


  public long getConfigHeaderId() {
    return configHeaderId;
  }

  public void setConfigHeaderId(long configHeaderId) {
    this.configHeaderId = configHeaderId;
  }


  public String getConfigNumber() {
    return configNumber;
  }

  public void setConfigNumber(String configNumber) {
    this.configNumber = configNumber;
  }


  public String getConfigStatus() {
    return configStatus;
  }

  public void setConfigStatus(String configStatus) {
    this.configStatus = configStatus;
  }


  public long getEnvId() {
    return envId;
  }

  public void setEnvId(long envId) {
    this.envId = envId;
  }


  public long getAppId() {
    return appId;
  }

  public void setAppId(long appId) {
    this.appId = appId;
  }

}

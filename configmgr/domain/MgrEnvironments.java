package com.yanqiancloud.control.configmgr.domain;


import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ModifyAudit
@VersionAudit
@Table(name = "mgr_environments")
public class MgrEnvironments {

  @Id
  @GeneratedValue
  private long envId;
  private String description;


  public long getEnvId() {
    return envId;
  }

  public void setEnvId(long envId) {
    this.envId = envId;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


}

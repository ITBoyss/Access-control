package com.yanqiancloud.control.configmgr.domain;


import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ModifyAudit
@VersionAudit
@Table(name = "mgr_configuration_lines")
public class MgrConfigurationLines {

  @Id
  @GeneratedValue
  private long configLineId;
  private long configHeaderId;
  private String configKey;
  private String configValue;


  public long getConfigLineId() {
    return configLineId;
  }

  public void setConfigLineId(long configLineId) {
    this.configLineId = configLineId;
  }


  public long getConfigHeaderId() {
    return configHeaderId;
  }

  public void setConfigHeaderId(long configHeaderId) {
    this.configHeaderId = configHeaderId;
  }


  public String getConfigKey() {
    return configKey;
  }

  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }


  public String getConfigValue() {
    return configValue;
  }

  public void setConfigValue(String configValue) {
    this.configValue = configValue;
  }


}

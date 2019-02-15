package com.yanqiancloud.control.configmgr.domain;


import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ModifyAudit
@VersionAudit
@Table(name = "mgr_config_publish")
public class MgrConfigPublish {

  @Id
  @GeneratedValue
  private long pubId;
  private long pubUserId;
  private long pubConfigId;
  private java.sql.Timestamp pubTime;
  private String pubType;



  public long getPubId() {
    return pubId;
  }

  public void setPubId(long pubId) {
    this.pubId = pubId;
  }


  public long getPubUserId() {
    return pubUserId;
  }

  public void setPubUserId(long pubUserId) {
    this.pubUserId = pubUserId;
  }


  public long getPubConfigId() {
    return pubConfigId;
  }

  public void setPubConfigId(long pubConfigId) {
    this.pubConfigId = pubConfigId;
  }


  public java.sql.Timestamp getPubTime() {
    return pubTime;
  }

  public void setPubTime(java.sql.Timestamp pubTime) {
    this.pubTime = pubTime;
  }


  public String getPubType() {
    return pubType;
  }

  public void setPubType(String pubType) {
    this.pubType = pubType;
  }


}

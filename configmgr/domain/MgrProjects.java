package com.yanqiancloud.control.configmgr.domain;


import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ModifyAudit
@VersionAudit
@Table(name = "mgr_projects")
public class MgrProjects {

  @Id
  @GeneratedValue
  private long prjId;
  private String prjName;
  private String prjCode;
  private String description;


  public long getPrjId() {
    return prjId;
  }

  public void setPrjId(long prjId) {
    this.prjId = prjId;
  }


  public String getPrjName() {
    return prjName;
  }

  public void setPrjName(String prjName) {
    this.prjName = prjName;
  }


  public String getPrjCode() {
    return prjCode;
  }

  public void setPrjCode(String prjCode) {
    this.prjCode = prjCode;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


}

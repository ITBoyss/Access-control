package com.yanqiancloud.control.configmgr.domain;

import com.yanqiancloud.mybatis.annotation.ModifyAudit;
import com.yanqiancloud.mybatis.annotation.VersionAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ModifyAudit
@VersionAudit
@Table(name = "sys_res_com_map")
public class SysResComMap {

    @Id
    @GeneratedValue
    private Long rescMapId;
    private Long rescId;
    private Long comId;


    public Long getRescMapId() {
        return rescMapId;
    }

    public void setRescMapId(Long rescMapId) {
        this.rescMapId = rescMapId;
    }


    public Long getRescId() {
        return rescId;
    }

    public void setRescId(Long rescId) {
        this.rescId = rescId;
    }


    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }


}

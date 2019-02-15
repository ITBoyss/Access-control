package com.yanqiancloud.control.configmgr.service;

import com.yanqiancloud.control.configmgr.domain.MgrApplications;
import com.yanqiancloud.core.domain.Page;
import com.yanqiancloud.mybatis.service.BaseService;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/22
 */
public interface IApplicationSrv extends BaseService<MgrApplications> {

    Page<MgrApplications> queryApplicationFuzzy(int page, int size, String sort, String appName);
}

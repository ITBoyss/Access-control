package com.yanqiancloud.control.configmgr.service.impl;

import com.yanqiancloud.control.configmgr.domain.MgrApplications;
import com.yanqiancloud.control.configmgr.service.IApplicationSrv;
import com.yanqiancloud.core.domain.Page;
import com.yanqiancloud.mybatis.service.BaseServiceImpl;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/22
 */
public class ApplicationSrvImpl extends BaseServiceImpl<MgrApplications> implements IApplicationSrv {

    @Override
    public Page<MgrApplications> queryApplicationFuzzy(int page, int size, String sort, String appName) {
        String[] sorts = sort.split(",");
        //排序需要的数据个数（一个排序字段，一个排序方向）
        final int sortsNum = 2;
        String sortField = sorts[0];
        String direction;
        //小于2表示只有排序字段，默认升序
        if (sorts.length < sortsNum) {
            direction = null;
        } else {
            direction = sorts[1];
        }

        return null;
    }
}

package com.yanqiancloud.control.configmgr.mapper;


import com.yanqiancloud.control.configmgr.domain.MgrApplications;
import com.yanqiancloud.mybatis.common.BaseMapper;
import feign.Param;

import java.util.List;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/22
 */
public interface MgrApplicationsMapper extends BaseMapper<MgrApplications> {

    /**
     * 根据应用名称模糊查询
     *
     * @param appName
     * @return
     */
    List<MgrApplications> queryApplicationsFuzzy(@Param(value = "sortField") String sortField,
                                                 @Param(value = "direction") String direction,
            @Param(value = "appName") String appName);
}

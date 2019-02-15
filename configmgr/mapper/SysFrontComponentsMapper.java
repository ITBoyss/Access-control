package com.yanqiancloud.control.configmgr.mapper;

import com.yanqiancloud.control.configmgr.domain.SysFrontComponents;
import com.yanqiancloud.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description 组件操作
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/22
 */
public interface SysFrontComponentsMapper extends BaseMapper<SysFrontComponents> {

    /**
     * @param list
     * @return
     * @description 查询当前用户的所有组件
     */
    List<SysFrontComponents> selectCurrentUserAllComponents(@Param("roleCodes") List<String> list);
}

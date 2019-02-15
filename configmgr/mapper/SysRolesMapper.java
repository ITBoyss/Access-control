package com.yanqiancloud.control.configmgr.mapper;

import com.yanqiancloud.control.configmgr.domain.SysRoles;
import com.yanqiancloud.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/22
 */
public interface SysRolesMapper extends BaseMapper<SysRoles> {

    /**
     * @param userId
     * @return
     * @description 根据用户ID和中间表查询角色
     */
    List<SysRoles> findByUserId(@Param("userId") Long userId);
}

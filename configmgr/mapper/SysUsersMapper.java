package com.yanqiancloud.control.configmgr.mapper;

import com.yanqiancloud.control.configmgr.domain.SysUsers;
import com.yanqiancloud.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description 用户操作
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/22
 */
public interface SysUsersMapper extends BaseMapper<SysUsers> {

    /**
     * @param userCode
     * @return
     * @description 根据用户名查询用户信息
     */
    SysUsers findByUserCode(@Param("userCode") String userCode);


    List<SysUsers> selectAllUser(@Param(value = "sortField") String sortField,
                                 @Param(value = "direction") String direction,
                                 @Param(value = "user") SysUsers sysUsers);

    SysUsers selectUserDetails(@Param("userId") Long userId);
}

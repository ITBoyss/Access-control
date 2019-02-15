package com.yanqiancloud.control.configmgr.service;

import com.yanqiancloud.control.configmgr.domain.SysUsers;
import com.yanqiancloud.core.domain.Page;
import com.yanqiancloud.mybatis.service.BaseService;

/**
 * @ProjectName: cloud-base
 * @Description: 用户信息业务接口
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/26 15:51
 * @Version: 1.0.0
 */
public interface ISysUsersService extends BaseService<SysUsers> {
    /**
     * @param sysUsers
     * @return
     * @Description: 添加用户
     */
    Long addUser(SysUsers sysUsers);

    /**
     * @param sysUsers
     * @return
     * @Description: 更新用户
     */
    Long updateUser(SysUsers sysUsers);

    /**
     * @param userId
     * @return
     * @Description: 删除用户
     */
    Long deleteUser(Long userId);

    /**
     * @param sysUsers
     * @return
     * @Description: 条件查询用户
     */
    Page<SysUsers> selectAllUser(int page, int size, String sort, SysUsers sysUsers);

    /**
     * @param userId
     * @return
     * @Description: 查询用户详情
     */
    SysUsers selectUserDetails(Long userId);
}

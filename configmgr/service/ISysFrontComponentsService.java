package com.yanqiancloud.control.configmgr.service;

import com.yanqiancloud.control.configmgr.domain.SysFrontComponents;
import com.yanqiancloud.mybatis.service.BaseService;

import java.util.List;

/**
 * @ProjectName: cloud-base
 * @Description: 组件服务
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/28 22:25
 * @Version: 1.0.0
 */
public interface ISysFrontComponentsService extends BaseService<SysFrontComponents> {
    /**
     * @return
     * @description 获取当前用户权限下的所有组件
     */
    List<SysFrontComponents> selectCurrentUserAllComponents();
}

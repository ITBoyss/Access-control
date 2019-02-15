package com.yanqiancloud.control.configmgr.service.impl;

import com.yanqiancloud.control.configmgr.domain.SysUsers;
import com.yanqiancloud.control.configmgr.mapper.SysUsersMapper;
import com.yanqiancloud.control.configmgr.service.ISysUsersService;
import com.yanqiancloud.core.domain.Page;
import com.yanqiancloud.core.domain.PageInfo;
import com.yanqiancloud.core.exception.CommonException;
import com.yanqiancloud.mybatis.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ProjectName: cloud-base
 * @Description: 用户信息业务实现
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/26 15:52
 * @Version: 1.0.0
 */
@Service
public class SysUsersServiceImpl extends BaseServiceImpl<SysUsers> implements ISysUsersService {

    @Autowired
    private SysUsersMapper usersMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addUser(SysUsers sysUsers) {
        sysUsers.setPassword(passwordEncoder.encode(sysUsers.getPassword()));
        try {
            usersMapper.insertSelective(sysUsers);
        } catch (CommonException e) {
            throw new CommonException("新增用户异常，请联系管理员");
        }
        return sysUsers.getUserId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long updateUser(SysUsers sysUsers) {
        if (!StringUtils.isEmpty(sysUsers.getPassword())) {
            sysUsers.setPassword(passwordEncoder.encode(sysUsers.getPassword()));
        }
        try {
            usersMapper.updateByPrimaryKeySelective(sysUsers);
        } catch (CommonException e) {
            throw new CommonException("更新用户异常，请联系管理员");
        }
        return sysUsers.getUserId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long deleteUser(Long userId) {
        SysUsers sysUsers = new SysUsers();
        sysUsers.setUserId(userId);
        sysUsers.setDeleteFlag(true);
        try {
            usersMapper.updateByPrimaryKeySelective(sysUsers);
        } catch (CommonException e) {
            throw new CommonException("删除用户异常，请联系管理员");
        }
        return userId;
    }

    @Override
    public Page<SysUsers> selectAllUser(int page, int size, String sort, SysUsers sysUsers) {
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
        List<SysUsers> users = usersMapper.selectAllUser(sortField, direction, sysUsers);
        PageInfo pageInfo = new PageInfo(page, size, true);
        Page<SysUsers> pageObject = new Page<>(users, pageInfo, users.size());
        return pageObject;
    }

    @Override
    public SysUsers selectUserDetails(Long userId) {
        SysUsers sysUsers = usersMapper.selectUserDetails(userId);
        return sysUsers;
    }
}

package com.yanqiancloud.control.sysmanager.user;

import com.yanqiancloud.control.configmgr.domain.SysFrontComponents;
import com.yanqiancloud.control.configmgr.service.ISysFrontComponentsService;
import com.yanqiancloud.core.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: cloud-base
 * @Description: 组件调度
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/29 8:48
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/sys/component")
public class ComponentController {

    @Autowired
    private ISysFrontComponentsService componentsService;


    @GetMapping("/current/display")
    @ResponseBody
    @ApiOperation("获取当前用户权限组件")
    public ResponseEntity<List<SysFrontComponents>> selectCurrentUserAllComponents() {

        return Optional.ofNullable(componentsService.selectCurrentUserAllComponents())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(NotFoundException::new);
    }
}

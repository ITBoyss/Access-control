package com.yanqiancloud.control.sysmanager.user;

import com.yanqiancloud.control.configmgr.domain.SysUsers;
import com.yanqiancloud.control.configmgr.service.ISysUsersService;
import com.yanqiancloud.core.domain.Page;
import com.yanqiancloud.core.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @ProjectName: cloud-base
 * @Description: 用户操作
 * @Author: WeiLingYun
 * @CreateDate: 2018/11/26 15:33
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private ISysUsersService usersService;

    @PostMapping
    @ResponseBody
    @ApiOperation("新增用户")
    public ResponseEntity<Long> addUser(@RequestBody SysUsers sysUsers) {
        return Optional.ofNullable(usersService.addUser(sysUsers))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(NotFoundException::new);
    }

    @PutMapping
    @ResponseBody
    @ApiOperation("更新用户")
    public ResponseEntity<Long> updateUser(@RequestBody SysUsers sysUsers) {
        return Optional.ofNullable(usersService.updateUser(sysUsers))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    @ApiOperation("删除用户")
    public ResponseEntity<Long> deleteUser(@PathVariable("userId") Long userId) {
        return Optional.ofNullable(usersService.deleteUser(userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    @ResponseBody
    @ApiOperation("查询用户列表")
    public ResponseEntity<Page<SysUsers>> selectAllUser(@RequestParam(name = "sort") String sort,
                                                        @RequestParam(name = "page") int page,
                                                        @RequestParam(name = "size") int size,
                                                        SysUsers sysUsers) {
        return Optional.ofNullable(usersService.selectAllUser(page, size, sort, sysUsers))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping("/{userId}")
    @ResponseBody
    @ApiOperation("查询用户详情")
    public ResponseEntity<SysUsers> selectUserDetails(@PathVariable("userId") Long userId) {
        return Optional.ofNullable(usersService.selectUserDetails(userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(NotFoundException::new);
    }
}

package com.yanqiancloud.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/10
 */
@SpringBootApplication
@EnableConfigServer
@EnableFeignClients
public class ControlServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControlServerApplication.class, args);

    }
}

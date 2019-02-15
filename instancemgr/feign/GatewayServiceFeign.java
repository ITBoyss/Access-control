package com.yanqiancloud.control.instancemgr.feign;

import com.yanqiancloud.control.instancemgr.domain.GatewayRouteDefinition;
import com.yanqiancloud.control.instancemgr.feign.fallback.GatewayFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/12
 */
@FeignClient(name = "yanqian-gateway-server" ,fallback = GatewayFeignFallback.class)
public interface GatewayServiceFeign {

    @PostMapping("/route")
    String add(@RequestBody GatewayRouteDefinition gwdefinition);
}
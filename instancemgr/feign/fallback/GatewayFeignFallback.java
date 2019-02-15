package com.yanqiancloud.control.instancemgr.feign.fallback;

import com.yanqiancloud.control.instancemgr.domain.GatewayRouteDefinition;
import com.yanqiancloud.control.instancemgr.feign.GatewayServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/12
 */

public class GatewayFeignFallback implements GatewayServiceFeign {
    private Logger logger = LoggerFactory.getLogger(GatewayFeignFallback.class);
    @Override
    public String add(GatewayRouteDefinition gwdefinition) {
        logger.error("refresh gateway failed . gateway was Unavailable .try again after a few minutes");
        return null;
    }
}

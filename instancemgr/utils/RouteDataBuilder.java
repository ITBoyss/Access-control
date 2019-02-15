package com.yanqiancloud.control.instancemgr.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanqiancloud.control.instancemgr.domain.GatewayFilterDefinition;
import com.yanqiancloud.control.instancemgr.domain.GatewayPredicateDefinition;
import com.yanqiancloud.control.instancemgr.domain.GatewayRouteDefinition;
import com.yanqiancloud.swagger.domain.GatewayRouteData;
import com.yanqiancloud.swagger.swagger.extra.ExtraData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * description todo
 *
 * @author 林金锁 Kinser Lin
 * @date 2018/11/12
 */
@Component
public class RouteDataBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDataBuilder.class);
    private ObjectMapper objectMapper = new ObjectMapper();


    private List<GatewayPredicateDefinition> buildPathPredicate(String path) {
        List<GatewayPredicateDefinition> gatewayPredicateDefinitions = new ArrayList<>();
        GatewayPredicateDefinition gatewayPredicateDefinition = new GatewayPredicateDefinition();
        Map<String, String> args = new LinkedHashMap<>();
        gatewayPredicateDefinition.setName("Path");
        args.put("pattern", path);
        gatewayPredicateDefinition.setArgs(args);
        gatewayPredicateDefinitions.add(gatewayPredicateDefinition);
        return gatewayPredicateDefinitions;
    }

    private GatewayFilterDefinition buildFilter() {
        return null;
    }

    public GatewayRouteDefinition buildRoute(String docJson) {
        ExtraData extraData;
        Map docJsonMap = null;
        GatewayRouteData gatewayRouteData;
        GatewayPredicateDefinition gatewayPredicateDefinition;
        GatewayRouteDefinition gatewayRouteDefinition  = new GatewayRouteDefinition();
        String serviceName = null;
        List<GatewayPredicateDefinition> gatewayPredicateDefinitions = new ArrayList<>();;


        try {
            docJsonMap = objectMapper.readValue(docJson, Map.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        if (docJsonMap != null) {
            Object object = docJsonMap.get(ExtraData.EXTRA_DATA_KEY);
            if (object != null) {
                extraData = objectMapper.convertValue(object, ExtraData.class);
                if (extraData != null) {

                    gatewayRouteData = objectMapper.convertValue(extraData.getData().get(ExtraData.ROUTE_DATA), GatewayRouteData.class);
                    if (gatewayRouteData != null && !gatewayRouteData.getPath().isEmpty()) {
                        gatewayPredicateDefinitions =
                        // 1 获取路由
                        gatewayPredicateDefinitions = this.buildPathPredicate(gatewayRouteData.getPath());
                        // 2 获取过滤配置，暂不考虑单服务过滤。只有全局过滤器，由网关配置

                        // 3 获取限流配置


                        // 4 获取服务名

                        serviceName = gatewayRouteData.getServiceName();
                    }


                    // 5 build 路由信息
                    gatewayRouteDefinition.setId(serviceName);
                    gatewayRouteDefinition.setServiceName(serviceName);
                    gatewayRouteDefinition.setPredicates(gatewayPredicateDefinitions);
                    return gatewayRouteDefinition;
                }
            }
        }
        return null;
    }
}
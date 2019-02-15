package com.yanqiancloud.control.instancemgr.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanqiancloud.control.instancemgr.domain.RegisterInstancePayload;
import com.yanqiancloud.control.instancemgr.feign.GatewayServiceFeign;
import com.yanqiancloud.control.instancemgr.service.IDocumentService;
import com.yanqiancloud.control.instancemgr.utils.RouteDataBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * eureka-instance消息队列的新消息监听处理
 *
 * @author jinsuolin
 */
@Component
@RefreshScope
public class EurekaInstanceListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaInstanceListener.class);
    private static final String INSTANCE_STATUS_UP = "UP";
    private static final String INSTANCE_UP_TOPIC = "instance-up";

    private final ObjectMapper mapper = new ObjectMapper();
    @Value("${yanqian.instance.listen.skip-service}")
    private String[] skipServices;
    private final IDocumentService documentService;

    private final RouteDataBuilder routeDataBuilder;

    private final GatewayServiceFeign gatewayServiceFeign;

    @Autowired
    public EurekaInstanceListener(IDocumentService documentService, RouteDataBuilder routeDataBuilder, GatewayServiceFeign gatewayServiceFeign) {
        this.documentService = documentService;
        this.routeDataBuilder = routeDataBuilder;
        this.gatewayServiceFeign = gatewayServiceFeign;
    }

    /**
     * 监听eureka-instance消息队列的新消息处理
     *
     * @param record 消息信息
     */
    @KafkaListener(topics = INSTANCE_UP_TOPIC)
    public void handle(ConsumerRecord<byte[], byte[]> record) {

        String message = new String(record.value());
        try {
            LOGGER.info("receive message from eureka-server, {}", message);
            RegisterInstancePayload payload = mapper.readValue(message, RegisterInstancePayload.class);
            if (!INSTANCE_STATUS_UP.equals(payload.getStatus())) {
                LOGGER.info("skip message that status is not up, {}", payload);
                return;
            }
            boolean isSkipService =
                    Arrays.stream(skipServices).anyMatch(t -> t.equals(payload.getAppName()));
            if (isSkipService) {
                LOGGER.info("skip message that is skipServices, {}", payload);
                return;
            }
            msgConsumer(payload);
        } catch (IOException e) {
            LOGGER.error("error happened when handle message， {} cause {}", message, e.getCause());
        }
    }

    private void msgConsumer(final RegisterInstancePayload instancePayload) {

        String json = null;

        try {
            json = documentService.fetchSwaggerJson(instancePayload);
        }catch (Exception e){
            LOGGER.error("get swagger json error ."+e.getMessage());
        }

        if (StringUtils.isEmpty(json)) {
            LOGGER.info("fetched swagger json data is empty, {}", instancePayload);
        } else {

            try {
                //1 更新路由配置到数据库
                LOGGER.info(instancePayload.getAppName()+": update control server routes.");

                //2 刷新网关路由
                LOGGER.info(instancePayload.getAppName()+": refresh gateway memory routes.");
                gatewayServiceFeign.add(routeDataBuilder.buildRoute(json));
            }catch (Exception e){
                LOGGER.error(instancePayload.getAppName()+": refresh gateway memory routes."+e.getMessage());
            }
        }
    }

}
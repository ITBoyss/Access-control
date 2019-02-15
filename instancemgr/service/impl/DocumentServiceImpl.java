package com.yanqiancloud.control.instancemgr.service.impl;

import com.yanqiancloud.control.instancemgr.domain.RegisterInstancePayload;
import com.yanqiancloud.control.instancemgr.service.IDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * {@inheritDoc}
 *
 * @author xausky
 * @author wuguokai
 */
@Service
public class DocumentServiceImpl implements IDocumentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private static final String SWAGGER_API_PATH = "/v2/yanqian/api-docs";


    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public String fetchSwaggerJson(RegisterInstancePayload payload) {
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity("http://"+payload.getInstanceAddress()+SWAGGER_API_PATH,String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception e) {
            LOGGER.error("error.fetchSwaggerJson {}", e.getMessage());
        }

        return null;
    }
}

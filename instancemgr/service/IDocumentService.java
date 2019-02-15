package com.yanqiancloud.control.instancemgr.service;

import com.yanqiancloud.control.instancemgr.domain.RegisterInstancePayload;

/**
 * swagger json的业务service
 *
 * @author xausky
 * @author wuguokai
 */
public interface IDocumentService {

    String fetchSwaggerJson(RegisterInstancePayload payload);

}

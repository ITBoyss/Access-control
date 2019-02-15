package com.yanqiancloud.control.instancemgr.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class GatewayPredicateDefinition {

    /**
     *  谓词名称
     */
    private String name;
    //
    /**
     *  配置的谓词规则
     */
    private Map<String, String> args = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }
}

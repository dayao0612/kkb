package com.kkb.mybatis.framework.config;

import lombok.Data;

/**
 * 封装了#{}解析出来的参数名称和参数类型
 */
public class ParameterMapping {
    private String name;
    private Class type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public ParameterMapping(String name) {
        this.name = name;
    }
}

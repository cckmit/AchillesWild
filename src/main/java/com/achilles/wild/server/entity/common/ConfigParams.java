package com.achilles.wild.server.entity.common;

import com.achilles.wild.server.entity.BaseEntity;

public class ConfigParams extends BaseEntity {

    private String key;

    private String val;

    private String description;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

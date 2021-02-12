package com.achilles.wild.server.business.entity.user;

import com.achilles.wild.server.business.entity.BaseEntity;

public class UserDetail extends BaseEntity {

    private String uuid;

    private String idNo;

    private String realName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
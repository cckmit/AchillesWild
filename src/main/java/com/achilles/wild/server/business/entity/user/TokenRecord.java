package com.achilles.wild.server.business.entity.user;

import com.achilles.wild.server.business.entity.BaseEntity;

public class TokenRecord extends BaseEntity {

    private String userUuid;

    private String token;

    private String terminalId;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
}
package com.achilles.wild.server.model.response.user;

import java.io.Serializable;

public class UserResponse implements Serializable {

    private static final long serialVersionUID = 2486303997613356730L;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

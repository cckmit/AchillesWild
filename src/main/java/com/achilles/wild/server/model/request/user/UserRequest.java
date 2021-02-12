package com.achilles.wild.server.model.request.user;

import com.achilles.wild.server.model.request.BaseRequest;

public class UserRequest extends BaseRequest {

    private String password;

    private String email;

    private String mobile;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

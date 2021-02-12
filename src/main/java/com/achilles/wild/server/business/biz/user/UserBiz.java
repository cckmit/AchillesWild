package com.achilles.wild.server.business.biz.user;

import com.achilles.wild.server.model.response.DataResult;

public interface UserBiz {

    DataResult<String> login(String email,String password);
}

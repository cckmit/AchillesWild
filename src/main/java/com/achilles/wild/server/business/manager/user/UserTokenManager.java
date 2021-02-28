package com.achilles.wild.server.business.manager.user;

import com.achilles.wild.server.entity.user.UserToken;

public interface UserTokenManager {

    Boolean addTokenRecord(UserToken userToken);


    UserToken getByToken(String token);


    Boolean updateById(UserToken userToken);
}

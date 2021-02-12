package com.achilles.wild.server.business.manager.user;

import com.achilles.wild.server.business.entity.user.TokenRecord;

public interface TokenRecordManager {

    Boolean addTokenRecord(TokenRecord tokenRecord);


    TokenRecord getByToken(String token);


    Boolean updateById(TokenRecord tokenRecord);
}

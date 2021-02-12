package com.achilles.wild.server.business.manager.user.impl;

import com.achilles.wild.server.business.dao.user.TokenRecordDao;
import com.achilles.wild.server.business.entity.user.TokenRecord;
import com.achilles.wild.server.business.manager.user.TokenRecordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TokenRecordManagerImpl implements TokenRecordManager {

    @Autowired
    private TokenRecordDao tokenRecordDao;

    @Override
    public Boolean addTokenRecord(TokenRecord tokenRecord) {

        Assert.notNull(tokenRecord,"token can not be null");

        int insert = tokenRecordDao.insertSelective(tokenRecord);
        if (insert==1){
            return true;
        }

        return false;
    }
}

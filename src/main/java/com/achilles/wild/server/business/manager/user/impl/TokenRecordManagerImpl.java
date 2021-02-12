package com.achilles.wild.server.business.manager.user.impl;

import com.achilles.wild.server.business.dao.user.TokenRecordDao;
import com.achilles.wild.server.business.entity.user.TokenRecord;
import com.achilles.wild.server.business.manager.user.TokenRecordManager;
import com.achilles.wild.server.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

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

    @Override
    public TokenRecord getByToken(String token) {

        Assert.hasLength(token,"token can not be null");

        List<TokenRecord> tokenRecordList = tokenRecordDao.selectByToken(token, StatusEnum.NORMAL.toNumbericValue());
        if (tokenRecordList.size()==0) {
            return null;
        }

        return tokenRecordList.get(0);
    }

    @Override
    public Boolean updateById(TokenRecord tokenRecord) {

        Assert.notNull(tokenRecord.getId()," TokenRecord id can not be null");

        int update = tokenRecordDao.updateByPrimaryKeySelective(tokenRecord);
        if(update==1){
            return true;
        }
        return false;
    }
}

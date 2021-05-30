package com.achilles.wild.server.business.manager.user.impl;

import com.achilles.wild.server.business.dao.user.TokenRecordDao;
import com.achilles.wild.server.entity.user.UserToken;
import com.achilles.wild.server.business.manager.user.UserTokenManager;
import com.achilles.wild.server.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class UserTokenManagerImpl implements UserTokenManager {

    @Autowired
    private TokenRecordDao tokenRecordDao;

    @Override
    public Boolean addTokenRecord(UserToken userToken) {

        Assert.notNull(userToken,"token can not be null");

        userToken.setStatus(StatusEnum.NORMAL.toNumbericValue());
        userToken.setCreateDate(new Date());
        userToken.setUpdateDate(new Date());
        int insert = tokenRecordDao.insertSelective(userToken);
        if (insert==1){
            return true;
        }

        return false;
    }

    @Override
    public UserToken getByToken(String token) {

        Assert.hasLength(token,"token can not be null");

        List<UserToken> userTokenList = tokenRecordDao.selectByToken(token, StatusEnum.NORMAL.toNumbericValue());
        if (userTokenList.size()==0) {
            return null;
        }

        return userTokenList.get(0);
    }

    @Override
    public Boolean updateById(UserToken userToken) {

        Assert.notNull(userToken.getId()," TokenRecord id can not be null");

        userToken.setUpdateDate(new Date());
        int update = tokenRecordDao.updateByPrimaryKeySelective(userToken);
        if(update==1){
            return true;
        }
        return false;
    }
}

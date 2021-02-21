package com.achilles.wild.server.business.manager.user.impl;

import com.achilles.wild.server.business.dao.user.UserDao;
import com.achilles.wild.server.entity.user.User;
import com.achilles.wild.server.business.manager.user.UserManager;
import com.achilles.wild.server.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByEmail(String email) {

        Assert.hasLength(email,"email can not be null !");

        List<User> userList = userDao.selectUserByEmail(email, StatusEnum.NORMAL.toNumbericValue());
        if (userList.size()==0){
            return null;
        }

        return userList.get(0);
    }
}

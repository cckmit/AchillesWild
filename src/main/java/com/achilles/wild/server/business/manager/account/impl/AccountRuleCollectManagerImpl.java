package com.achilles.wild.server.business.manager.account.impl;

import com.achilles.wild.server.business.dao.account.AccountRuleCollectDao;
import com.achilles.wild.server.entity.account.AccountRuleCollect;
import com.achilles.wild.server.business.manager.account.AccountRuleCollectManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountRuleCollectManagerImpl implements AccountRuleCollectManager {


    @Resource
    private AccountRuleCollectDao accountRuleCollectDao;

    @Override
    public List<String> getAccountFromWeightByUser(String userId) {

        if(StringUtils.isEmpty(userId)){
            return new ArrayList<>();
        }

        List<AccountRuleCollect> accountRuleCollects = accountRuleCollectDao.selectRuleByUser(userId);

        if(CollectionUtils.isEmpty(accountRuleCollects)){
            return new ArrayList<>();
        }

        List<String> accountCodes = accountRuleCollects.stream().map(accountRuleCollect -> accountRuleCollect.getAccountCode()).collect(Collectors.toList());
        accountCodes = accountCodes.stream().sorted().collect(Collectors.toList());

        return accountCodes;
    }
}

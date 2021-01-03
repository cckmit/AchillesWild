package com.achilles.wild.server.dao.account;

import java.util.List;

import com.achilles.wild.server.entity.account.AccountRuleCollect;
import org.apache.ibatis.annotations.Param;

public interface AccountRuleCollectDao {

    int insertSelective(AccountRuleCollect record);


    List<AccountRuleCollect> selectRuleByUser( @Param("userId") String userId);

}
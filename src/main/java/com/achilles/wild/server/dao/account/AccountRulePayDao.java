package com.achilles.wild.server.dao.account;

import java.util.List;

import com.achilles.wild.server.entity.account.AccountRulePay;
import org.apache.ibatis.annotations.Param;

public interface AccountRulePayDao {

    int insertSelective(AccountRulePay record);


    List<AccountRulePay> selectRuleByUser( @Param("userId") String userId);

}
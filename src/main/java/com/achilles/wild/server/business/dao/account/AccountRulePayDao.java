package com.achilles.wild.server.business.dao.account;

import com.achilles.wild.server.entity.account.AccountRulePay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountRulePayDao {

    int insertSelective(AccountRulePay record);


    List<AccountRulePay> selectRuleByUser( @Param("userId") String userId);

}
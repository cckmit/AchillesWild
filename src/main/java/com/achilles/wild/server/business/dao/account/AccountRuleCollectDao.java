package com.achilles.wild.server.business.dao.account;

import com.achilles.wild.server.business.entity.account.AccountRuleCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountRuleCollectDao {

    int insertSelective(AccountRuleCollect record);


    List<AccountRuleCollect> selectRuleByUser( @Param("userId") String userId);

}
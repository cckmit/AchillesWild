package com.achilles.wild.server.business.dao.account;

import com.achilles.wild.server.entity.account.AccountLock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountLockDao {

    int insertSelective(AccountLock record);

    AccountLock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountLock record);

    List<AccountLock> selectLockAccount(@Param("accountCode") String accountCode);

    int unlockAll();

}
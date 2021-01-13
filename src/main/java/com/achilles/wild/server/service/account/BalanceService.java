package com.achilles.wild.server.service.account;

import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.PageResult;

/**
 * �ո���
 */
public interface BalanceService {


    //DataResult<String> addUserBalance(BalanceRequest request);

    PageResult<String> consumeUserBalance(BalanceRequest request);

    PageResult<String> addInterBalance(BalanceRequest request);

    PageResult<String> consumeInterBalance(BalanceRequest request);


    Long getBalance(String userId);

    Long getInterBalance();

}

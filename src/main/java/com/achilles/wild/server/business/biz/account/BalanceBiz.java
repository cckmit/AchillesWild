package com.achilles.wild.server.business.biz.account;

import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.account.BalanceResponse;

public interface BalanceBiz {

    BalanceResponse reduce(BalanceRequest request);

    DataResult<String> add(BalanceRequest request);
}

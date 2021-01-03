package com.achilles.wild.server.service.account;

import com.achilles.wild.server.model.request.account.AccountRequest;
import com.achilles.wild.server.model.response.DataResult;

public interface AccountService {

    /**
     * ????????
     *
     * @param request
     * @return
     */
    DataResult addMasterAccount(AccountRequest request);

    /**
     * ?????????
     *
     * @param request
     * @return
     */
    DataResult addMasterAndSlaveAccount(AccountRequest request);


    /**
     * ?????????
     *
     * @param request
     * @return
     */
    DataResult addAllAccounts(AccountRequest request);


    DataResult addAccountsByType(AccountRequest request,int count);
}

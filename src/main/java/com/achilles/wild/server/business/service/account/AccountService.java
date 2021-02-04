package com.achilles.wild.server.business.service.account;

import com.achilles.wild.server.model.request.account.AccountRequest;
import com.achilles.wild.server.model.response.PageResult;

public interface AccountService {

    /**
     * ????????
     *
     * @param request
     * @return
     */
    PageResult addMasterAccount(AccountRequest request);

    /**
     * ?????????
     *
     * @param request
     * @return
     */
    PageResult addMasterAndSlaveAccount(AccountRequest request);


    /**
     * ?????????
     *
     * @param request
     * @return
     */
    PageResult addAllAccounts(AccountRequest request);


    PageResult addAccountsByType(AccountRequest request, int count);
}

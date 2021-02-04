package com.achilles.wild.server.business.manager.account;

import java.util.List;

public interface AccountRuleCollectManager {


    List<String> getAccountFromWeightByUser(String userId);
}

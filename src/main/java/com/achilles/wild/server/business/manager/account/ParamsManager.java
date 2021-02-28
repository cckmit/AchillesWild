package com.achilles.wild.server.business.manager.account;

import com.achilles.wild.server.entity.common.ConfigParams;

import java.util.List;

public interface ParamsManager {

    List<ConfigParams> selectAll();
}

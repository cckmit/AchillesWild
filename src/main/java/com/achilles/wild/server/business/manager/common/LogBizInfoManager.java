package com.achilles.wild.server.business.manager.common;

import com.achilles.wild.server.entity.common.LogBizInfo;

import java.util.List;

public interface LogBizInfoManager {

    boolean addLog(LogBizInfo log);

    boolean addLogs(List<LogBizInfo> list);
}

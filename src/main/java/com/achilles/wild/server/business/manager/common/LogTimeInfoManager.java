package com.achilles.wild.server.business.manager.common;

import com.achilles.wild.server.entity.common.LogTimeInfo;

import java.util.List;

public interface LogTimeInfoManager {

    boolean addLog(LogTimeInfo log);

    boolean addLogs(List<LogTimeInfo> logTimeInfoList);
}

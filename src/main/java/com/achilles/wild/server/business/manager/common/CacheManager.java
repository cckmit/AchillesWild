package com.achilles.wild.server.business.manager.common;

public interface CacheManager {

    boolean lock(String key,int seconds);

    boolean unLock(String key);
}

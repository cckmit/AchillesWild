package com.achilles.wild.server.dao;

public interface BaseDao<T> {
	public static final String DAO_NAME = "com.server.architect.base.dao.BaseDaoMybatis";
	
	public void delete(long id);
}

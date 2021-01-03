package com.achilles.wild.server.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class BaseDaoImpl<T> implements BaseDao<T>{
	
	private final static Logger logger =LoggerFactory.getLogger(BaseDaoImpl.class); 

	@Resource(name="sqlSession")
	private SqlSession sqlSession;

	public void delete(long id){
//		log.info(GetBeans.getSqlSession().delete("com.server.beans.system.Role.delete",id));
	}
	
}

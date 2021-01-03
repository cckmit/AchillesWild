package com.achilles.wild.server.manager.impl;

import java.util.List;

import com.achilles.wild.server.dao.info.CitizenDao;
import com.achilles.wild.server.entity.info.Citizen;
import com.google.common.collect.Lists;
import com.achilles.wild.server.manager.CitizenManager;
import com.achilles.wild.server.model.query.CitizenQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitizenManagerImpl implements CitizenManager {

	@Autowired
	private CitizenDao citizenDao;
	
	@Override
	public List<Citizen> addCitizens(List<Citizen> list) {
		List<List<Citizen>> listList = Lists.partition(list, 500);
		for (List<Citizen> subList : listList) {
			citizenDao.batchInsert(subList);
		}
	  return list;
	}

	@Override
	public List<Citizen> getListPage(CitizenQuery query) {
		if (query==null){
			query = new CitizenQuery();
		}
		List<Citizen> citizens = citizenDao.selectList(query);
		return citizens;
	}

	@Override
	public List<Citizen> getCitizenPage(CitizenQuery query) {
		if (query==null){
			query = new CitizenQuery();
		}
		return citizenDao.selectByList(query);
	}
}

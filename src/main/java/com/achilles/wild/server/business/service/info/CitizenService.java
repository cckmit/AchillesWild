package com.achilles.wild.server.business.service.info;

import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.entity.info.CrmClient;
import com.achilles.wild.server.entity.info.LcsMember;
import com.achilles.wild.server.model.query.info.CitizenQuery;
import com.achilles.wild.server.model.response.PageResult;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CitizenService {

	void addCitizen(JSONObject jsonOb);
	
	void addCitizen(LcsMember member);
	
	void addCitizen(CrmClient crmClient);

	Citizen addCitizen();
	
	List<Citizen> addCitizenList(List<Citizen> list);
	
	List<Citizen> addCitizens(List<Citizen> list);

	boolean saveCitizens(List<Citizen> list);

	PageResult<List<Citizen>> getList(CitizenQuery query);

	PageResult<List<Citizen>> getCitizens(CitizenQuery query);
	
}

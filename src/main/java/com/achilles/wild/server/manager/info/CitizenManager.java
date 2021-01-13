package com.achilles.wild.server.manager.info;

import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.model.query.info.CitizenQuery;

import java.util.List;

public interface CitizenManager {

	List<Citizen> addCitizens(List<Citizen> list);

	List<Citizen>  getListPage(CitizenQuery query);


	List<Citizen>  getCitizenPage(CitizenQuery query);
}

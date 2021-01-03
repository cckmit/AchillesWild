package com.achilles.wild.server.manager;

import java.util.List;

import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.model.query.CitizenQuery;

public interface CitizenManager {

	List<Citizen> addCitizens(List<Citizen> list);

	List<Citizen>  getListPage(CitizenQuery query);


	List<Citizen>  getCitizenPage(CitizenQuery query);
}

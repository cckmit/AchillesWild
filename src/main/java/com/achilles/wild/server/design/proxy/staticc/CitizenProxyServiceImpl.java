package com.achilles.wild.server.design.proxy.staticc;

import java.util.List;

import javax.annotation.Resource;

import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.entity.info.CrmClient;
import com.achilles.wild.server.entity.info.LcsMember;
import com.achilles.wild.server.service.info.CitizenService;
import com.alibaba.fastjson.JSONObject;

import com.achilles.wild.server.model.query.info.CitizenQuery;
import com.achilles.wild.server.model.response.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service
public class CitizenProxyServiceImpl implements CitizenService {

    private final static Logger LOG = LoggerFactory.getLogger(CitizenProxyServiceImpl.class);


    @Resource
    private CitizenService citizenService;


    @Override
    public void addCitizen(JSONObject jsonOb) {

    }

    @Override
    public void addCitizen(LcsMember member) {

    }

    @Override
    public void addCitizen(CrmClient crmClient) {

    }

    @Override
    public Citizen addCitizen() {
        return null;
    }

    @Override
    public List<Citizen> addCitizenList(List<Citizen> list) {
        return null;
    }

    @Override
    public List<Citizen> addCitizens(List<Citizen> list) {
        return null;
    }

    @Override
    public boolean saveCitizens(List<Citizen> list) {
        return false;
    }

    @Override
    public PageResult<List<Citizen>> getList(CitizenQuery query) {
        return null;
    }

    @Override
    public PageResult<List<Citizen>> getCitizens(CitizenQuery query) {

        LOG.info("get citizen proxy  start =====================================");

        PageResult<List<Citizen>> list = citizenService.getCitizens(query);


        LOG.info("get citizen proxy  over =====================================");

        return list;
    }
}

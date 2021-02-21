package com.achilles.wild.server.business.service.info.impl;

import java.util.List;
import java.util.concurrent.Callable;

import com.achilles.wild.server.business.dao.info.CitizenDao;
import com.achilles.wild.server.business.dao.info.CitizenDetailDao;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.alibaba.fastjson.JSONObject;

import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.entity.info.CitizenDetail;
import com.achilles.wild.server.entity.info.CrmClient;
import com.achilles.wild.server.entity.info.LcsMember;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.achilles.wild.server.business.manager.info.CitizenManager;
import com.achilles.wild.server.model.query.info.CitizenQuery;
import com.achilles.wild.server.model.response.PageResult;
import com.achilles.wild.server.business.service.info.CitizenService;
import com.achilles.wild.server.tool.date.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CitizenServiceImpl implements CitizenService, Callable<Integer> {

	private final static Logger LOG = LoggerFactory.getLogger(CitizenServiceImpl.class);

	@Autowired
	private CitizenDao citizenDao;
	@Autowired
	private CitizenDetailDao citizenDetailDao;
	
	@Autowired
	private CitizenManager citizenManager;

	public void setCitizenManager(CitizenManager citizenManager) {
		this.citizenManager = citizenManager;
	}

	private int m;

	public void setM(int m) {
		this.m = m;
	}

	private List<Citizen> citizenList;

	public void setCitizenList(List<Citizen> citizenList) {
		this.citizenList = citizenList;
	}

	private DataSourceTransactionManager transactionManager;

	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	@Transactional(rollbackForClassName ="Exception",isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public boolean saveCitizens(List<Citizen> list) {
		saveCitizen(list);
		return true;
	}

	private boolean saveCitizen(List<Citizen> list) {
		citizenManager.addCitizens(list);
		return true;
	}

	@Override
	public Integer call(){
		LOG.info("call    ********************   thread count  "+m);
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = getTransactionStatus(def);
		try {
			citizenManager.addCitizens(citizenList);
			transactionManager.commit(status);

		} catch (TransactionException e) {
			transactionManager.rollback(status);
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 手动事务
	 * @param def
	 * @return
	 */
	private TransactionStatus getTransactionStatus(DefaultTransactionDefinition def) {
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		def.setReadOnly(false);
		TransactionStatus status = transactionManager.getTransaction(def);
		return status;
	}

	@Override
	@Transactional(rollbackForClassName ="Exception",isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public List<Citizen> addCitizens(List<Citizen> list) {
		citizenManager.addCitizens(list);
		return list;
	}

	
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void addCitizen(JSONObject jsonOb) {
		 Citizen citizen = new Citizen();
		 String uuid = GenerateUniqueUtil.getUuId();
		 citizen.setUuid(uuid);
		 citizen.setName(jsonOb.get("realName").toString());
		 citizen.setIdNo(jsonOb.get("idCardNo").toString());
		 citizen.setRemark("CRM");
		 citizen.setCreateDate(DateUtil.getCurrentDate());
		 citizen.setUpdateDate(citizen.getCreateDate());
		 citizenDao.insertSelective(citizen);
		 CitizenDetail citizenDe = new CitizenDetail();
		 citizenDe.setUuid(uuid);
		 if (jsonOb.get("mobile")!=null) {
			 citizenDe.setMobile(jsonOb.get("mobile").toString());
		 }
		 if (jsonOb.get("nativePlace")!=null) {
			 citizenDe.setAddress(jsonOb.get("nativePlace").toString());
		 }
		 if (jsonOb.get("birthday")!=null) {
			 citizenDe.setBirthday(jsonOb.get("birthday").toString());
		 }				
		 citizenDe.setRemark("CRM");
		 citizenDe.setCreateDate(DateUtil.getCurrentDate());
		 citizenDe.setUpdateDate(citizenDe.getCreateDate());
		 citizenDetailDao.insertSelective(citizenDe);
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void addCitizen(LcsMember member) {
		Citizen citizen = new Citizen();
		 String uuid = GenerateUniqueUtil.getUuId();
		 citizen.setUuid(uuid);
		 citizen.setName(member.getRealName());
		 citizen.setIdNo(member.getIdcardNo());
		 citizen.setRemark("邀请码");
		 citizen.setCreateDate(DateUtil.getCurrentDate());
		 citizen.setUpdateDate(citizen.getCreateDate());
		 citizenDao.insertSelective(citizen);
		 CitizenDetail citizenDe = new CitizenDetail();
		 citizenDe.setUuid(uuid);
		 citizenDe.setMobile(member.getMobile());
		 citizenDe.setNickName(member.getNickName());
		 citizenDe.setAddress(member.getCityname());
		 citizenDe.setEmail(member.getEmail());
		 citizenDe.setRemark("邀请码");
		 citizenDe.setCreateDate(DateUtil.getCurrentDate());
		 citizenDe.setUpdateDate(citizenDe.getCreateDate());
		 citizenDetailDao.insertSelective(citizenDe);
	}
	
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void addCitizen(CrmClient crmClient) {
		Citizen citizen = new Citizen();
		 String uuid = GenerateUniqueUtil.getUuId();
		 citizen.setUuid(uuid);
		 citizen.setName(crmClient.getRealname());
		 citizen.setIdNo(crmClient.getIdcardno());
		 citizen.setRemark("CRM");
		 citizen.setCreateDate(DateUtil.getCurrentDate());
		 citizenDao.insertSelective(citizen);
		 CitizenDetail citizenDe = new CitizenDetail();
		 citizenDe.setUuid(uuid);
		 citizenDe.setMobile(crmClient.getMobile());
		 citizenDe.setAddress(crmClient.getNativeplace());
		 citizenDe.setEmail(crmClient.getEmail());
		 citizenDe.setBirthday(crmClient.getBirthday());
		 citizenDe.setRemark("CRM");
		 citizenDe.setCreateDate(DateUtil.getCurrentDate());
		 citizenDetailDao.insertSelective(citizenDe);
	}

	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public Citizen addCitizen() {
		Citizen citizen = new Citizen();
		String uuid = GenerateUniqueUtil.getUuId();
		citizen.setUuid(uuid);
		citizen.setName("兰尼斯特2");
		citizen.setIdNo("94937537");
		citizen.setRemark("test");
		citizen.setCreateDate(DateUtil.getCurrentDate());
		citizenDao.insertSelective(citizen);

		CitizenQuery query  = new CitizenQuery();
		query.setIdNo(citizen.getIdNo());
		query.setName(citizen.getName());
		Citizen citizen1 = citizenDao.selectByIdNoAndName(query);
		return citizen1;
	}
	
	@Override
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public List<Citizen> addCitizenList(List<Citizen> list) {
		List<List<Citizen>> listList = Lists.partition(list, 2);
		for (List<Citizen> subList : listList) {
			citizenDao.batchInsert(subList);
		}
	  return list;
	}

	@Override
	public PageResult<List<Citizen>> getList(CitizenQuery query) {

		Page page= PageHelper.startPage(query.getPageNo(), query.getPageSize());
		List<Citizen> citizens = citizenDao.selectList(query);
		if(CollectionUtils.isEmpty(citizens)){
			return PageResult.baseFail();
		}
		int total = Integer.parseInt(page.getTotal()+"");
		PageResult<List<Citizen>> result = PageResult.success(citizens,total);

		return result;
	}

	@Override
	public PageResult<List<Citizen>> getCitizens(CitizenQuery query) {

		query.setPaginator(CitizenQuery.getPaginator(query.getPageNo(),query.getPageSize()));

		List<Citizen> citizenList = citizenManager.getCitizenPage(query);
		if(CollectionUtils.isEmpty(citizenList)){
			return PageResult.baseFail();
		}

		PageResult<List<Citizen>> pageResult = new PageResult<>();
		pageResult.setData(citizenList);
		return pageResult;
	}
}

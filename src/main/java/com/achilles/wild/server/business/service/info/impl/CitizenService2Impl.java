package com.achilles.wild.server.business.service.info.impl;

import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.business.manager.info.CitizenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CitizenService2Impl implements Runnable {

	private final static Logger LOG = LoggerFactory.getLogger(CitizenService2Impl.class);
	
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
	public void run() {
		{
            LOG.info("call    ********************   thread count  "+m);
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = getTransactionStatus(def);
			try {
				citizenManager.addCitizens(citizenList);
				transactionManager.commit(status);

			} catch (TransactionException e) {
				transactionManager.rollback(status);
				e.printStackTrace();
			}
		}
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
}

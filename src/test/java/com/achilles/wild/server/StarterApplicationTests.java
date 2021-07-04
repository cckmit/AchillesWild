package com.achilles.wild.server;

import com.achilles.wild.server.business.dao.account.AccountDao;
import com.achilles.wild.server.enums.StatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StarterApplicationTests {

	private final static Logger log = LoggerFactory.getLogger(StarterApplicationTests.class);

	@Autowired
	AccountDao accountDao;


	@Test
	public void contextLoads() {
		Long bal = accountDao.selectUserBalance("wild", StatusEnum.NORMAL.toNumbericValue());
		java.lang.Class C;
		System.out.println();
	}

}

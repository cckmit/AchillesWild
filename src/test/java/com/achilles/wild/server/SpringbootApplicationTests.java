package com.achilles.wild.server;

import com.achilles.wild.server.business.dao.account.AccountDao;
import com.achilles.wild.server.other.queue.disruptor.DisruptorMqService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	private final static Logger log = LoggerFactory.getLogger(SpringbootApplicationTests.class);

	@Autowired
	AccountDao accountDao;

	@Autowired
	DisruptorMqService disruptorMqService;
	/**
	 * 项目内部使用Disruptor做消息队列
	 * @throws Exception
	 */
	@Test
	public void sayHelloMqTest() throws Exception{
		disruptorMqService.sayHelloMq("消息到了，Hello world!");
		log.info("消息队列已发送完毕");
		//这里停止2000ms是为了确定是处理消息是异步的
		Thread.sleep(2000);
	}

	@Test
	public void contextLoads() {
		Long bal = accountDao.selectUserBalance("wild");
		java.lang.Class C;
		System.out.println();
	}

}

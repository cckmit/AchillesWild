package com.achilles.wild.server.tool;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
//@Transactional
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class BaseSpringJUnitTest{
	
	private final static Logger log = LoggerFactory.getLogger(BaseSpringJUnitTest.class); 
	
    @Before  
    public void before(){  
    	log.info("=======================load  		applicationContext.xml    ===============================");
    }  
    
//    @Resource
//    DatasService datasService;
    
//    @Autowired
//    DatasService datasService;
    
//    @Test
//    public  void test(){
//    	log.info("datasService  ====="+datasService);
//    }
}

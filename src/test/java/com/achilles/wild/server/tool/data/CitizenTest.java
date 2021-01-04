package com.achilles.wild.server.tool.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.achilles.wild.server.entity.info.Citizen;
import com.github.rholder.retry.Retryer;
import com.achilles.wild.server.manager.CitizenManager;
import com.achilles.wild.server.model.query.CitizenQuery;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.service.CitizenService;
import com.achilles.wild.server.task.RetryAddCitizensTask;
import com.achilles.wild.server.tool.BaseSpringJUnitTest;
import com.achilles.wild.server.tool.date.DateConstant;
import com.achilles.wild.server.tool.date.DateUtil;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.achilles.wild.server.tool.jdbc.MySqlUtil;
import com.achilles.wild.server.tool.jdbc.SqlUtil;
import com.achilles.wild.server.tool.json.FastJsonUtil;
import com.achilles.wild.server.tool.retry.RetryUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class CitizenTest  extends BaseSpringJUnitTest{

	private final static Logger LOG = LoggerFactory.getLogger(CitizenTest.class);

	@Resource
	private CitizenService citizenService;

	@Resource
	private CitizenManager citizenManager;

	@Resource(name="innerTxManager")
	private DataSourceTransactionManager transactionManager;

	@Test
	public void getCitizens(){
		CitizenQuery query = getCitizenQuery();
		DataResult<List<Citizen>> data = citizenService.getCitizens(query);
		System.out.println();
	}

	private CitizenQuery getCitizenQuery() {
		CitizenQuery query = new CitizenQuery();
		//query.setName("skdwn");
		query.setPageNo(1);
		query.setPageSize(10);
		return query;
	}

	@Test
	public void getListPage(){
		CitizenQuery query =getCitizenQuery();
		DataResult<List<Citizen>> data = citizenService.getList(query);
		System.out.println();
	}

	@Test
	public void addCitizenRunnableTest() throws Exception{
		List<Citizen> list = getCitizenList(1);
		RetryAddCitizensTask.addCitizenRunnable(citizenManager,transactionManager,list);
        Thread.sleep(5000L);
	}

	@Test
	public void addCitizenRetryTest(){
		String now = DateUtil.getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS);

		Retryer<Boolean> retry = RetryUtil.getRetryCount(1, 3,"入库");
		//Retryer<Boolean> retry = getRetryTimeOut(1, 4,"store ");
		//Retryer<Integer> retry = RetryUtil.getRetryNeverStop(10, "store ");
		List<Citizen> list = getCitizenList(300000);
		Boolean result;
		try {
			result = retry.call(() -> citizenService.saveCitizens(list));
			LOG.info("==========================result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("starts:    "+now+"     ---------------   ends:   "+DateUtil.getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS));
	}

	@Test
	public void addCitizensRetryTest(){
		String now = DateUtil.getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS);
		//Retryer<Boolean> retry = getRetryCount(1, 3,"入库");
		//Retryer<Boolean> retry = getRetryTimeOut(1, 4,"store ");
		Retryer<Integer> retry = RetryUtil.getRetryNeverStop(10, "store ");
		List<Citizen> list = getCitizenList(300000);
		try {
			//重试入口采用call方法，用的是java.util.concurrent.Callable<V>的call方法,所以执行是线程安全的
			Integer result = retry.call(() -> RetryAddCitizensTask.addCitizenTask(citizenManager,transactionManager,list));
			LOG.info("==========================result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("starts:    "+now+"     ---------------   ends:   "+DateUtil.getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS));
	}

	@Test
	public void addCitizensThreadTest(){
		String now = DateUtil.getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS);

		List<Citizen> list = getCitizenList(200000);
		RetryAddCitizensTask.addCitizenTask(citizenManager,transactionManager,list);

		LOG.info("starts:    "+now+"     ---------------   ends:   "+DateUtil.getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS));

	}

	private List<Citizen> getCitizenList(int num){
		List<Citizen> list = new ArrayList<Citizen>();
		for (int i = 0; i < num; i++) {
			Citizen citizen = new Citizen();
			String uuid = GenerateUniqueUtil.getUuId();
			citizen.setUuid(uuid);
			citizen.setName("Achilles_"+System.currentTimeMillis());
			citizen.setIdNo("94937537"+i);
			citizen.setRemark("test");
			citizen.setCreateDate(DateUtil.getCurrentDate());
			list.add(citizen);
		}
		return list;
	}

	@Test
	public void addCitizensTest(){
		String now = DateUtil.getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS);
		List<Citizen> list = getCitizenList(2);
		citizenService.addCitizens(list);
		 Thread thread = Thread.currentThread();
		ThreadLocal local = new ThreadLocal();
		Object val =local.get();


		System.out.println("STARTS:    "+now+"     ***************************   ENDS:   "+DateUtil.getCurrentStr(DateConstant.FORMAT_YYYY_MM_DD_HHMMSS));
	}
	
	@Test
	public void addCitizenListTest(){
		List<Citizen> list = getCitizenList(6);
		citizenService.addCitizenList(list);
	}
	
	@Test
	public void addCitizen(){
		citizenService.addCitizen();
	}

	@Test
	public void getJsonToDb() {//          "  ?滻?\\"                     <\\/a>   </a>
		//1.???excel????-------------------------------------------------
//		 String url = "C://Users//Administrator//Desktop//a.xlsx";
//		Map<String,String> params = new HashMap<>();
//		params.put("page", "1");
//		params.put("rows", "2");
		String s1 ="";
		List<Map<String, Object>> list =  FastJsonUtil.getListMap(s1);
		String s2 ="";
		List<Map<String, Object>> list2 =  FastJsonUtil.getListMap(s2);
		String s3 ="";
		List<Map<String, Object>> list3 =  FastJsonUtil.getListMap(s3);
		list3.addAll(list2);
		list3.addAll(list);
		//2.???????excel????????????----------------------------------------
		 List<String> sqlList = new ArrayList<String>();
		 for (Map<String, Object> map : list3) {
			 Map<String,Object> params = new HashMap<String,Object>();
			 String uuid = GenerateUniqueUtil.getUuId();
			 params.put("uuid", uuid);
			 params.put("name", map.get("real_name"));
			 params.put("sex", map.get("sex")==null?"2":map.get("sex").toString());
			 params.put("id_no", map.get("idcard_no"));
			 params.put("remark", "??????");
			 String insertSql = SqlUtil.getInsertSql("test.citizen", params);
			 sqlList.add(insertSql);
			 params = new HashMap<String,Object>();
			 params.put("uuid", uuid);
			 params.put("mobile", map.get("mobile"));
			 params.put("nick_name", map.get("nick_name"));
			 params.put("address", map.get("cityname"));
			 params.put("email", "email");
			insertSql = SqlUtil.getInsertSql("test.citizen_detail", params);
			sqlList.add(insertSql);
			
		}
		 String val = MySqlUtil.updateBat(sqlList);
//		 System.out.println(val);
	}
}

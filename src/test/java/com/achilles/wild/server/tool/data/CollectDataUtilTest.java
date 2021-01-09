package com.achilles.wild.server.tool.data;

import com.achilles.wild.server.SpringbootApplicationTests;
import com.achilles.wild.server.tool.data.collect.CollectDataUtil;
import com.achilles.wild.server.tool.file.excel.ExcelReadUtil;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.achilles.wild.server.tool.http.HttpPostUtil;
import com.achilles.wild.server.tool.jdbc.MySqlUtil;
import com.achilles.wild.server.tool.jdbc.SqlUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.*;

public class CollectDataUtilTest extends SpringbootApplicationTests {

	String str = "#$1870141 庞庞 庞  464哈82rth is牛eo$1870匹配1514亓 官648*";
	@Test
	public void get9fTest() {
		List<Map<String,Object>> memList = MySqlUtil.getListBysql("SELECT * FROM lcs_member");
		for (Map<String, Object> memMap : memList) {
			String idCardNo = memMap.get("idcard_no") ==null?"":memMap.get("idcard_no").toString();
			String name = memMap.get("real_name") ==null?"":memMap.get("real_name").toString();
			String inviteCode = memMap.get("invite_code") ==null?"":memMap.get("invite_code").toString();
			if ("".equals(idCardNo)||"".equals(name)||"".equals(inviteCode)) {
				continue;
			}
			Map<String,Object> crmParams=new HashMap<String,Object>();
			crmParams.put("invitationCode",inviteCode);
			crmParams.put("page","1");
			crmParams.put("size","10000");
			String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/pageSearchClient", crmParams);
			if (retStr==null||"".equals(retStr)) {
				continue;
			}
			JSONObject jsonObj = JSON.parseObject(retStr);
			if (!jsonObj.getBooleanValue("success")) {
				continue;
			}
			JSONArray jsonArray = jsonObj.getJSONArray("content");
			System.out.println("--------------------客户数:"+jsonArray.size());
			List<String> sqlList = new ArrayList<String>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonOb = jsonArray.getJSONObject(i);
				String crmName = jsonOb.get("realName") ==null?"":jsonOb.get("realName").toString();
				String crmIdCardNo = jsonOb.get("idCardNo") ==null?"":jsonOb.get("idCardNo").toString();
				if (crmIdCardNo.length()<6) {
					continue;
				}
				System.out.println("--------------------crmName:"+crmName+",crmIdCardNo:"+crmIdCardNo);
				if ("".equals(crmName)||"".equals(crmIdCardNo)) {
					continue;
				}
				Map<String,Object> citizenMap = MySqlUtil.getMapBysql("SELECT*FROM citizen WHERE name='"+crmName+"' AND id_no LIKE '%"+crmIdCardNo+"'");
				String updateSql = null;
				String updateSqlDe = null;
				if (citizenMap!=null) {
					continue;
				}
				 Map<String,Object> params = new HashMap<String,Object>();
				 String uuid = GenerateUniqueUtil.getUuId();
				 params.put("uuid", uuid);
				 params.put("name", crmName);
				 params.put("id_no", crmIdCardNo);
				 params.put("remark", "邀请码");
				 updateSql = SqlUtil.getInsertSql("info.citizen", params);
				 params = new HashMap<String,Object>();
				 params.put("uuid", uuid);
				 params.put("mobile", jsonOb.get("mobile"));
				 params.put("birthday", jsonOb.get("birthday"));
				 params.put("address", jsonOb.get("nativePlace"));
				 params.put("remark", "CRM");
				 updateSqlDe = SqlUtil.getInsertSql("info.citizen_detail", params);
				 sqlList.add(updateSql);
				 sqlList.add(updateSqlDe);
			}
			MySqlUtil.updateBatch(sqlList);
		}
	}
	
	
	@Test
	public void getMemInfoTest() {
		List<Map<String,Object>> memList = MySqlUtil.getListBysql("SELECT * FROM lcs_member");
		for (Map<String, Object> memMap : memList) {
			String idCardNo = memMap.get("idcard_no") ==null?"":memMap.get("idcard_no").toString();
			if (idCardNo.length()<6) {
				continue;
			}
			String name = memMap.get("real_name") ==null?"":memMap.get("real_name").toString();
			if ("".equals(idCardNo)||"".equals(name)) {
				continue;
			}
			Map<String,Object> citizenMap = MySqlUtil.getMapBysql("SELECT*FROM citizen WHERE name='"+name+"' AND id_no LIKE '%"+idCardNo+"'");
			String updateSql = null;
			String updateSqlDe = null;
			if (citizenMap==null) {
				 Map<String,Object> params = new HashMap<String,Object>();
				 String uuid = GenerateUniqueUtil.getUuId();
				 params.put("uuid", uuid);
				 params.put("name", memMap.get("real_name"));
				 params.put("id_no", memMap.get("idcard_no"));
				 params.put("remark", "邀请码");
				 updateSql = SqlUtil.getInsertSql("info.citizen", params);
				 params = new HashMap<String,Object>();
				 params.put("uuid", uuid);
				 params.put("mobile", memMap.get("mobile"));
				 params.put("nick_name", memMap.get("nick_name"));
				 params.put("address", memMap.get("cityName"));
				 params.put("email", memMap.get("email"));
				 params.put("remark", "邀请码");
				 updateSqlDe = SqlUtil.getInsertSql("info.citizen_detail", params);
			}else{
				String idCardNoCi = citizenMap.get("idcard_no") ==null?"":citizenMap.get("idcard_no").toString();
				if (idCardNo.length()>idCardNoCi.length()) {
					updateSql ="UPDATE info.citizen set id_no='"+idCardNo+"' where id="+citizenMap.get("id");
				}
			}
			 MySqlUtil.update(updateSql);
			 MySqlUtil.update(updateSqlDe);
		}
	}
	
	@Test
	public void collectChineseNameToSetTest() {
//		System.out.println(collectOneNumToStr(str));
//		System.out.println(collectMobileToList(collectOneNumToStr(str)));
//		System.out.println(collectDataToStr(str, rule));
//		System.out.println(collectOneChineseToStr(str));
		System.out.println(CollectDataUtil.collectChineseNameToSet(str, "庞"));
	}
	
	@Test
	public void getCountTest() {
		System.out.println(CollectDataUtil.getCount(str, "$"));
	}
	
	@Test
	public void getInviteCodeFromExcelTest() {
		Set set =new HashSet();
		set.add(1);
		set.add(2);
//		String url = "C://Users//Administrator//Desktop//a.xls";
//		String url = "C://Users//Achil//Desktop//网络.xls";
//		String inviteCodeSet = ExcelReadUtil.readExcelForSet(url,2,3).toString().replaceAll("\\s", "").replaceAll(",", "|").replace("[", "").replace("]", "");
//		System.out.println(TxtUtil.writeByLineLength("C://Users//Achil//Desktop//网络.txt",inviteCodeSet,3000));
		
//		String url = "C://Users//Achil//Desktop//职能.xls";
//		String inviteCodeSet = ExcelReadUtil.readExcelForSet(url,2,3).toString().replaceAll("\\s", "").replaceAll(",", "|").replace("[", "").replace("]", "");
//		System.out.println(TxtUtil.writeByLineLength("C://Users//Achil//Desktop//职能.txt",inviteCodeSet,3000));
		
		String url = "C://Users//Administrator//Desktop//01.xlsx";
//		String inviteCodeSet = ExcelReadUtil.readExcelForSet(url,2,1).toString().replaceAll("\\s", "").replaceAll(",", "|").replace("[", "").replace("]", "");
//		System.out.println(TxtUtil.writeByLineLength("C://Users//Achil//Desktop//销售.txt",inviteCodeSet,150));
		String inviteCodeSet = ExcelReadUtil.readExcelForSet(url,2,1).toString();
		System.out.println(inviteCodeSet);
	}
	
	@Test
	public void getMobileFromExcelTest() {
//		String url = "C://Users//Administrator//Desktop//a.xls";
		String url = "C://Users//Achil//Desktop//a.xls";
		System.out.println(CollectDataUtil.collectMobileToList(ExcelReadUtil.readExcelForSet(url,2,5).toString()));
	}
}

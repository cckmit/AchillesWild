package com.tool.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.achilles.wild.server.dao.info.CitizenDao;
import com.achilles.wild.server.dao.info.CitizenDetailDao;
import com.achilles.wild.server.dao.info.CrmClientDao;
import com.achilles.wild.server.dao.info.CrmOrderDao;
import com.achilles.wild.server.dao.info.LcsMemberDao;
import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.entity.info.CitizenDetail;
import com.achilles.wild.server.entity.info.CrmClient;
import com.achilles.wild.server.entity.info.CrmOrder;
import com.achilles.wild.server.entity.info.LcsMember;
import com.achilles.wild.server.service.CitizenService;
import com.tool.BaseSpringJUnitTest;
import com.achilles.wild.server.tool.date.DateUtil;
import com.achilles.wild.server.tool.file.excel.ExcelReadUtil;
import com.achilles.wild.server.tool.file.excel.ExcelWriteUtil;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.achilles.wild.server.tool.http.HttpPostUtil;

public class CollectCitizenInfoTest extends BaseSpringJUnitTest{
	
	private final static Logger log =LoggerFactory.getLogger(CollectCitizenInfoTest.class);

	@Autowired
	private LcsMemberDao lcsMemberDao;
	@Autowired
	private CitizenDao citizenDao;
	@Autowired
	private CitizenDetailDao citizenDetailDao;
	@Autowired
	private CitizenService citizenService;
	@Autowired
	private CrmClientDao crmClientDao;
	@Autowired
	private CrmOrderDao crmOrderDao;
	
	@Test
	public void getCrmClientToCitizenTest2() {
		String url = "C://Users//Administrator//Desktop//a.xlsx";
		Set<String> set = ExcelReadUtil.readExcelForSet(url,2,2);
		StringBuffer sb = new StringBuffer();
		BigDecimal sum = BigDecimal.ZERO;
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		for (String code : set) {
			BigDecimal total = BigDecimal.ZERO;
			Map<String,Object> crmParams=new HashMap<String,Object>();
			crmParams.put("invitationCode",code);
			crmParams.put("page","1");
			crmParams.put("size","15000");
			String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/pageSearchClient", crmParams);
			if (retStr==null||"".equals(retStr)) {
				continue;
			}
			JSONObject jsonObj = JSON.parseObject(retStr);
			if (!jsonObj.getBooleanValue("success")) {
				continue;
			}
			JSONArray jsonArray = jsonObj.getJSONArray("content");
			if (jsonArray==null||jsonArray.size()==0) {
				continue;
			}
//			if (jsonArray.size()>5000) {
//				log.info("-------------客户超过5000的-------code:"+code+"-----客户数:"+jsonArray.size());
//			}
			log.info("---------------code:"+code+"-----客户数:"+jsonArray.size());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject clientJsonOb = jsonArray.getJSONObject(i);
//				String crmName = clientJsonOb.get("realName").toString();
//				String crmIdCardNo = clientJsonOb.get("idCardNo").toString();
				crmParams=new HashMap<String,Object>();
				crmParams.put("memberId",clientJsonOb.getInteger("id"));
				crmParams.put("startTime","2017-06-16 00:00:00");
				crmParams.put("endTime","2019-01-16 23:59:59");
				crmParams.put("orderStatus","0");
				crmParams.put("page","1");
				crmParams.put("size","15000");
				String retStr2 = HttpPostUtil.postJson("https://crm.9f.cn/interface/queryOrderInfo", crmParams);
				if (retStr2==null||"".equals(retStr2)) {
					continue;
				}
				JSONObject orderObj = JSON.parseObject(retStr2);
				if (!jsonObj.getBooleanValue("success")) {
					continue;
				}
				JSONObject jsonData = orderObj.getJSONObject("data");
				if (jsonData==null) {
					continue;
				}
				JSONArray orderJsonArray = jsonData.getJSONArray("mOrderDTOList");
				if (orderJsonArray==null || orderJsonArray.size()==0) {
					continue;
				}
				log.info("-------------code:"+code+",id:"+clientJsonOb.getInteger("id")+",count:"+orderJsonArray.size());
//				if (orderJsonArray.size()>5000) {
//					log.info("-------------订单超过5000的-------code:"+code+",id:"+clientJsonOb.getInteger("id")+",count:"+orderJsonArray.size());
//				}
				for (int m = 0; m < orderJsonArray.size(); m++) {
					JSONObject jsonOb = orderJsonArray.getJSONObject(m);
					total = total.add(jsonOb.getBigDecimal("payAmount")==null?BigDecimal.ZERO:jsonOb.getBigDecimal("payAmount"));
				}
			}
			log.info("--------------------total:"+sb.toString());
			sb.append(code+"="+total+",");
			sum = sum.add(total);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("invite_code", code);
			params.put("total", total);
			dataList.add(params);
		}
		log.info("--------------------sum:"+sum+"||"+sb.toString());
		ExcelWriteUtil.write("C://Users//Administrator//Desktop//sd.xlsx",dataList);
	}
	
	@Test
	public void get9fOrderTest() {
		Date start = DateUtil.getCurrentDate();
		List<CrmClient> clientList = crmClientDao.selectList();
		for (CrmClient client : clientList) {
			if (client.getId()==null) {
				continue;
			}
			Map<String,Object> crmParams=new HashMap<String,Object>();
			crmParams.put("memberId",client.getId());
			crmParams.put("startTime","2018-07-01 00:00:00");
			crmParams.put("endTime","2018-07-30 23:59:59");
			crmParams.put("page","1");
			crmParams.put("size","10000");
			String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/queryOrderInfo", crmParams);
			if (retStr==null||"".equals(retStr)) {
				continue;
			}
			JSONObject jsonObj = JSON.parseObject(retStr);
			if (!jsonObj.getBooleanValue("success")) {
				continue;
			}
			JSONObject jsonData = jsonObj.getJSONObject("data");
			if (jsonData==null) {
				continue;
			}
			JSONArray jsonArray = jsonData.getJSONArray("mOrderDTOList");
			if (jsonArray==null) {
				continue;
			}
//			if (jsonArray.size()>20) {
				log.info("--------------------订单数:"+jsonArray.size());
//			}
			
			CrmOrder orderQuy = new CrmOrder();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonOb = jsonArray.getJSONObject(i);
				if (jsonOb.get("orderNo")==null) {
					continue;
				}
				orderQuy.setMemberid(client.getId());
				orderQuy.setOrderno(jsonOb.get("orderNo").toString());
				Long id = crmOrderDao.selectIdByMemIdAndNo(orderQuy);
				CrmOrder crmOrder = new CrmOrder();;
				if (id!=null) {
					crmOrder.setId(id);
				}
				addOrUpdateOrder(crmOrder, jsonOb);
			}
		}
		Date end = DateUtil.getCurrentDate();
		System.out.println("-------------------开始:"+start+",结束:"+end);
	}
	
	@Test
	public void get9fClientTest() {
		Date start = DateUtil.getCurrentDate();
		List<LcsMember> memList = lcsMemberDao.selectList();
		for (LcsMember mem : memList) {
			String idCardNo = mem.getIdcardNo() ==null?"":mem.getIdcardNo().toString();
			String name = mem.getRealName() ==null?"":mem.getRealName().toString();
			String inviteCode = mem.getInviteCode() ==null?"":mem.getInviteCode().toString();
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
			log.info("--------------------客户数:"+jsonArray.size());
			CrmClient clientQuy = new CrmClient();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonOb = jsonArray.getJSONObject(i);
				if (jsonOb.get("realName")==null||jsonOb.get("idCardNo")==null) {
					continue;
				}
				String crmName = jsonOb.get("realName").toString();
				String crmIdCardNo = jsonOb.get("idCardNo").toString();
				if (crmIdCardNo.length()<6) {
					continue;
				}
				log.info("--------------------crmName:"+crmName+",crmIdCardNo:"+crmIdCardNo);
				if ("".equals(crmName)||"".equals(crmIdCardNo)) {
					continue;
				}
				clientQuy.setInviteCode(mem.getInviteCode());
				clientQuy.setIdcardno(crmIdCardNo);
				clientQuy.setRealname(crmName);
				CrmClient citizenPre = crmClientDao.selectByIdNoAndName(clientQuy);
				CrmClient client = new CrmClient();
				if (citizenPre!=null) {
					client.setIdd(citizenPre.getIdd());;
				}
				addOrUpdateCrmClient(client, mem, jsonOb);
			}
		}
		log.info("-------------------开始:"+start+",结束:"+DateUtil.getCurrentDate());
	}
	
	@Test
	public void getCrmClientToCitizenTest() {
		Date start = DateUtil.getCurrentDate();
		List<CrmClient> crmClientList = crmClientDao.selectList();
		Citizen citizenQuy = new Citizen();
		Citizen citizenUp = new Citizen();
		CitizenDetail citizenDe = new CitizenDetail();
		for (int i = 0; i < crmClientList.size(); i++) {
			log.info("---------------------------------"+i);
			CrmClient crmClient = crmClientList.get(i);
			String idCardNo = crmClient.getIdcardno() ==null?"":crmClient.getIdcardno().toString();
			String name = crmClient.getRealname() ==null?"":crmClient.getRealname().toString();
			if (idCardNo.length()<6 || "".equals(name)) {
				continue;
			}
			citizenQuy.setIdNo(idCardNo);
			citizenQuy.setName(name);
			Citizen citizenPre = citizenDao.selectByIdNoAndName(citizenQuy);
			if (citizenPre==null) {
				 citizenService.addCitizen(crmClient);
			}else{
				if (idCardNo.length()>citizenPre.getIdNo().length()) {
					citizenUp.setId(citizenPre.getId());
					citizenUp.setIdNo(citizenPre.getIdNo());
					citizenDao.updateByPrimaryKeySelective(citizenUp);
				 }
				 citizenDe.setUuid(citizenPre.getUuid());
				 citizenDe.setMobile(crmClient.getMobile());
				 citizenDe.setAddress(crmClient.getNativeplace());
				 citizenDe.setEmail(crmClient.getEmail());
				 citizenDe.setBirthday(crmClient.getBirthday());
				 citizenDetailDao.updateByUuidSelective(citizenDe);
			}
		}
		log.info("-------------------开始:"+start+",结束:"+DateUtil.getCurrentDate());
	}
	
	@Test
	public void getMemToCitizenTest() {
		Date start = DateUtil.getCurrentDate();
		List<LcsMember> memList = lcsMemberDao.selectList();
		Citizen citizenQuy = new Citizen();
		Citizen citizenUp = new Citizen();
		CitizenDetail citizenDe = new CitizenDetail();
		for (int i = 0; i < memList.size(); i++) {
			log.info("---------------------------------"+i);
			LcsMember member = memList.get(i);
			String idCardNo = member.getIdcardNo() ==null?"":member.getIdcardNo().toString();
			if (idCardNo.length()<6) {
				continue;
			}
			String name = member.getRealName() ==null?"":member.getRealName().toString();
			if ("".equals(idCardNo)||"".equals(name)) {
				continue;
			}
			citizenQuy.setIdNo(idCardNo);
			citizenQuy.setName(name);
			Citizen citizenPre = citizenDao.selectByIdNoAndName(citizenQuy);
			if (citizenPre==null) {
				 citizenService.addCitizen(member);
			}else{
				if (idCardNo.length()>citizenPre.getIdNo().length()) {
					citizenUp.setId(citizenPre.getId());
					citizenUp.setIdNo(citizenPre.getIdNo());
					citizenDao.updateByPrimaryKeySelective(citizenUp);
				 }
				 citizenDe.setUuid(citizenPre.getUuid());
				 citizenDe.setMobile(member.getMobile());
				 citizenDe.setNickName(member.getNickName());
				 citizenDe.setAddress(member.getCityname());
				 citizenDe.setEmail(member.getEmail());
				 citizenDetailDao.updateByUuidSelective(citizenDe);
			}
		}
		log.info("-------------------开始:"+start+",结束:"+DateUtil.getCurrentDate());
	}
	
	private void addOrUpdateCrmClient(CrmClient client,LcsMember mem,JSONObject jsonOb){
		client.setInviteCode(mem.getInviteCode());
		client.setId(jsonOb.get("id").toString());
		
		client.setInviteCode(mem.getInviteCode());
		client.setId(jsonOb.get("id").toString());
		if (jsonOb.get("realName")!=null) {
			client.setRealname(jsonOb.get("realName").toString());	
		}
		if (jsonOb.get("mobile")!=null) {
			client.setMobile(jsonOb.get("mobile").toString());	
		}
		if (jsonOb.get("idCardNo")!=null) {
			client.setIdcardno(jsonOb.get("idCardNo").toString());	
		}
		if (jsonOb.get("birthday")!=null) {
			client.setBirthday(jsonOb.get("birthday").toString());	
		}
		if (jsonOb.get("age")!=null) {
			client.setAge(jsonOb.get("age").toString());	
		}
		if (jsonOb.get("sex")!=null) {
			client.setSex(jsonOb.get("sex").toString());	
		}
		if (jsonOb.get("email")!=null) {
			client.setEmail(jsonOb.get("email").toString());	
		}
		if (jsonOb.get("registerTime")!=null) {
			client.setRegistertime(jsonOb.get("registerTime").toString());	
		}
		if (jsonOb.get("bindTime")!=null) {
			client.setBindtime(jsonOb.get("bindTime").toString());	
		}
		if (jsonOb.get("nativePlace")!=null) {
			client.setNativeplace(jsonOb.get("nativePlace").toString());	
		}
		if (jsonOb.get("isReal")!=null) {
			client.setIsreal(jsonOb.get("isReal").toString());	
		}
		if (jsonOb.get("attributesVersion")!=null) {
			client.setAttributesversion(jsonOb.get("attributesVersion").toString());	
		}
		if (jsonOb.get("delFlag")!=null) {
			client.setDelflag(jsonOb.get("delFlag").toString());	
		}
		if (jsonOb.get("isLive")!=null) {
			client.setIslive(jsonOb.get("isLive").toString());	
		}
		if (jsonOb.get("spendInCount")!=null) {
			client.setSpendincount(jsonOb.get("spendInCount").toString());	
		}
		if (jsonOb.get("channelCode")!=null) {
			client.setChannelcode(jsonOb.get("channelCode").toString());	
		}
		if (jsonOb.get("fromMemberId")!=null) {
			client.setFrommemberid(jsonOb.get("fromMemberId").toString());	
		}
		if (jsonOb.get("withdrawPwd")!=null) {
			client.setWithdrawpwd(jsonOb.get("withdrawPwd").toString());	
		}
		if (jsonOb.get("spendCount")!=null) {
			client.setSpendcount(jsonOb.get("spendCount").toString());	
		}
		if (jsonOb.get("updateTime")!=null) {
			client.setUpdatetime(jsonOb.get("updateTime").toString());	
		}
		if (jsonOb.get("wuId")!=null) {
			client.setWuid(jsonOb.get("wuId").toString());	
		}
		if (jsonOb.get("createTime")!=null) {
			client.setCreatetime(jsonOb.get("createTime").toString());	
		}
		if (jsonOb.get("isHf")!=null) {
			client.setIshf(jsonOb.get("isHf").toString());	
		}
		if (jsonOb.get("attributes")!=null) {
			client.setAttributes(jsonOb.get("attributes").toString());	
		}
		if (jsonOb.get("flag")!=null) {
			client.setFlag(jsonOb.get("flag").toString());	
		}	
		if (client.getIdd()!=null) {
			client.setInviteCode(null);
			client.setRealname(null);
			crmClientDao.updateByPrimaryKeySelective(client);
		}else{
			client.setUuid(GenerateUniqueUtil.getUuId());
			client.setCreateTime(DateUtil.getCurrentDate());
			crmClientDao.insertSelective(client);
		}
	}
	
	private void addOrUpdateOrder(CrmOrder crmOrder,JSONObject jsonOb){
		crmOrder.setMemberid(jsonOb.get("memberId").toString());
		crmOrder.setOrderno(jsonOb.get("orderNo").toString());	
		if (jsonOb.get("sourceOrderNo")!=null) {
			crmOrder.setSourceorderno(jsonOb.get("sourceOrderNo").toString());	
		}
		if (jsonOb.get("payStatusEnum")!=null) {
			crmOrder.setPaystatusenum(jsonOb.get("payStatusEnum").toString());	
		}
		if (jsonOb.get("spendTime")!=null) {
			crmOrder.setSpendtime(jsonOb.get("spendTime").toString());	
		}
		if (jsonOb.get("interestTime")!=null) {
			crmOrder.setInteresttime(jsonOb.get("interestTime").toString());	
		}
		if (jsonOb.get("redemptionTime")!=null) {
			crmOrder.setRedemptiontime(jsonOb.get("redemptionTime").toString());	
		}
		if (jsonOb.get("period")!=null) {
			crmOrder.setPeriod(jsonOb.get("period").toString());	
		}
		if (jsonOb.get("payAmount")!=null) {
			crmOrder.setPayamount(jsonOb.get("payAmount").toString());	
		}
		if (jsonOb.get("spendAmount")!=null) {
			crmOrder.setSpendamount(jsonOb.get("spendAmount").toString());	
		}
		if (jsonOb.get("productId")!=null) {
			crmOrder.setProductid(jsonOb.get("productId").toString());	
		}
		if (jsonOb.get("productName")!=null) {
			crmOrder.setProductname(jsonOb.get("productName").toString());	
		}
		if (jsonOb.get("orderStatusEnum")!=null) {
			crmOrder.setOrderstatusenum(jsonOb.get("orderStatusEnum").toString());	
		}
		if (jsonOb.get("parentOrderNo")!=null) {
			crmOrder.setParentorderno(jsonOb.get("parentOrderNo").toString());	
		}
		if (jsonOb.get("createTime")!=null) {
			crmOrder.setCreatetime(jsonOb.get("createTime").toString());	
		}
		if (jsonOb.get("continueProductId")!=null) {
			crmOrder.setContinueproductid(jsonOb.get("continueProductId").toString());	
		}
		if (jsonOb.get("countProfit")!=null) {
			crmOrder.setCountprofit(jsonOb.get("countProfit").toString());	
		}
		if (jsonOb.get("expandProfit")!=null) {
			crmOrder.setExpandprofit(jsonOb.get("expandProfit").toString());	
		}
		if (jsonOb.get("expctedEarning")!=null) {
			crmOrder.setExpctedearning(jsonOb.get("expctedEarning").toString());	
		}
		if (jsonOb.get("exprieProcessMode")!=null) {
			crmOrder.setExprieprocessmode(jsonOb.get("exprieProcessMode").toString());	
		}
		if (jsonOb.get("exprieProcessModeEnum")!=null) {
			crmOrder.setExprieprocessmodeenum(jsonOb.get("exprieProcessModeEnum").toString());	
		}
		if (jsonOb.get("haveparent")!=null) {
			crmOrder.setHaveparent(jsonOb.get("haveparent").toString());	
		}
		if (jsonOb.get("makertProfit")!=null) {
			crmOrder.setMakertprofit(jsonOb.get("makertProfit").toString());	
		}
		if (jsonOb.get("newProduct")!=null) {
			crmOrder.setNewproduct(jsonOb.get("newProduct").toString());	
		}
		if (jsonOb.get("orderChannelEnum")!=null) {
			crmOrder.setOrderchannelenum(jsonOb.get("orderChannelEnum").toString());	
		}
		if (jsonOb.get("oredrProfit")!=null) {
			crmOrder.setOredrprofit(jsonOb.get("oredrProfit").toString());	
		}	
		
		if (jsonOb.get("payModeEnum")!=null) {
			crmOrder.setPaymodeenum(jsonOb.get("payModeEnum").toString());	
		}
		if (jsonOb.get("productCat")!=null) {
			crmOrder.setProductcat(jsonOb.get("productCat").toString());	
		}
		if (jsonOb.get("productCatCodeEnum")!=null) {
			crmOrder.setProductcatcodeenum(jsonOb.get("productCatCodeEnum").toString());	
		}
		if (jsonOb.get("profit")!=null) {
			crmOrder.setProfit(jsonOb.get("profit").toString());	
		}
		if (jsonOb.get("tradeNo")!=null) {
			crmOrder.setTradeno(jsonOb.get("tradeNo").toString());	
		}	
		if (jsonOb.get("userCoupon")!=null) {
			crmOrder.setUsercoupon(jsonOb.get("userCoupon").toString());	
		}	
		if (crmOrder.getId()!=null) {
			crmOrder.setOrderno(null);
			crmOrder.setMemberid(null);
			crmOrderDao.updateByPrimaryKeySelective(crmOrder);
		}else{
			crmOrder.setUuid(GenerateUniqueUtil.getUuId());
			crmOrder.setCreateTime(DateUtil.getCurrentDate());
			crmOrderDao.insertSelective(crmOrder);
		}
	}
	
//	@Test
//	public void get9fToCitizenTest() {
//		List<LcsMember> memList = lcsMemberDao.selectList();
//		for (LcsMember mem : memList) {
//			String idCardNo = mem.getIdcardNo() ==null?"":mem.getIdcardNo().toString();
//			String name = mem.getRealName() ==null?"":mem.getRealName().toString();
//			String inviteCode = mem.getInviteCode() ==null?"":mem.getInviteCode().toString();
//			if ("".equals(idCardNo)||"".equals(name)||"".equals(inviteCode)) {
//				continue;
//			}
//			Map<String,Object> crmParams=new HashMap<String,Object>();
//			crmParams.put("invitationCode",inviteCode);
//			crmParams.put("page","1");
//			crmParams.put("size","10000");
//			String retStr = HttpPostUtil.postJson("https://crm.9f.cn/interface/pageSearchClient", crmParams);
//			if (retStr==null||"".equals(retStr)) {
//				continue;
//			}
//			JSONObject jsonObj = JSON.parseObject(retStr);
//			if (!jsonObj.getBooleanValue("success")) {
//				continue;
//			}
//			JSONArray jsonArray = jsonObj.getJSONArray("content");
//			System.out.println("--------------------客户数:"+jsonArray.size());
//			Citizen citizenQuy = new Citizen();
//			for (int i = 0; i < jsonArray.size(); i++) {
//				JSONObject jsonOb = jsonArray.getJSONObject(i);
//				if (jsonOb.get("realName")==null||jsonOb.get("idCardNo")==null) {
//					continue;
//				}
//				String crmName = jsonOb.get("realName").toString();
//				String crmIdCardNo = jsonOb.get("idCardNo").toString();
//				if (crmIdCardNo.length()<6) {
//					continue;
//				}
////				System.out.println("--------------------crmName:"+crmName+",crmIdCardNo:"+crmIdCardNo);
//				if ("".equals(crmName)||"".equals(crmIdCardNo)) {
//					continue;
//				}
//				citizenQuy.setIdNo(crmIdCardNo);
//				citizenQuy.setName(crmName);
//				Citizen citizenPre = citizenDao.selectByIdNoAndName(citizenQuy);
//				if (citizenPre!=null) {
//					continue;
//				}
//				citizenService.addCitizen(jsonOb);
//			}
//		}
//	}
}

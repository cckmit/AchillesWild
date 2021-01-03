package com.achilles.wild.server.tool.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpGetUtil {
	private final static Logger log = LoggerFactory.getLogger(HttpGetUtil.class);

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Map<String, String> params = new HashMap<>();
//		params.put("name", URLEncoder.encode("希腊", "UTF-8"));
		params.put("name", "希腊");
//		params.put("name", "admin");
		params.put("place", "aaa");
		params.put("age", "34");
		String url = "http://localhost:8080/maven/mvc/service/testController/testGet";
		get(url, params);
	}

	public static String get(String url, Map<String, String> params) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		NameValuePair[] nameValuePairs = new NameValuePair[params.size()];
		int m = 0;
		for (String key : params.keySet()) {
			nameValuePairs[m] = new NameValuePair(key, params.get(key));
			m++;
		}
		getMethod.setQueryString(nameValuePairs);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);// 超时为5秒
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		log.info("请求URL=" + url + "  入参=" + params);
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = null;
		try {
			str = getMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			getMethod.releaseConnection();
		}
		log.info("返回请求Code:" + statusCode);
//		log.info("返回:"+str);
		return str;
	}

}

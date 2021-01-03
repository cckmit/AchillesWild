package com.achilles.wild.server.tool.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * 
 * @author Administrator
 *
 */
public class GsonUtil {

	/**
	 * 实现格式化的时间字符串转时间对象
	 */
	private static final String DATEFORMAT_default = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 使用默认的gson对象进行反序列化
	 * 
	 * @param json
	 * @param typeToken
	 * @return
	 */
	public static <T> T fromJsonDefault(String json, TypeToken<T> typeToken) {
		Gson gson = new Gson();
		return gson.fromJson(json, typeToken.getType());
	}

	/**
	 * json字符串转list或者map
	 * 
	 * @param json
	 * @param typeToken
	 * @return
	 */
	public static <T> T fromJson(String json, TypeToken<T> typeToken) {

		Gson gson = new GsonBuilder()
		/**
		 * 重写map的反序列化
		 */
		.registerTypeAdapter(new TypeToken<Map<String, Object>>() {
		}.getType(), new MapTypeAdapter()).create();

		return gson.fromJson(json, typeToken.getType());

	}

	/**
	 * json字符串转bean对象
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> cls) {
		Gson gson = new GsonBuilder().setDateFormat(DATEFORMAT_default).create();
		return gson.fromJson(json, cls);
	}
	
	
	public static String toJson(Object obj) {
		return toJson(obj,false);
	}

	/**
	 * 对象转json
	 * 
	 * @param obj
	 * @param format
	 * @return
	 */
	public static String toJson(Object obj, boolean format) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		/**
		 * 设置默认时间格式
		 */
		gsonBuilder.setDateFormat(DATEFORMAT_default);

		/**
		 * 添加格式化设置
		 */
		if (format) {
			gsonBuilder.setPrettyPrinting();
		}

		Gson gson = gsonBuilder.create();

		return gson.toJson(obj);
	}
	
	

	public static class MapTypeAdapter extends TypeAdapter<Object> {

		@Override
		public Object read(JsonReader in) throws IOException {
			JsonToken token = in.peek();
			switch (token) {
			case BEGIN_ARRAY:
				List<Object> list = new ArrayList<Object>();
				in.beginArray();
				while (in.hasNext()) {
					list.add(read(in));
				}
				in.endArray();
				return list;

			case BEGIN_OBJECT:
				Map<String, Object> map = new LinkedTreeMap<String, Object>();
				in.beginObject();
				while (in.hasNext()) {
					map.put(in.nextName(), read(in));
				}
				in.endObject();
				return map;

			case STRING:
				return in.nextString();

			case NUMBER:
				/**
				 * 改写数字的处理逻辑，将数字值分为整型与浮点型。
				 */
				double dbNum = in.nextDouble();

				// 数字超过long的最大值，返回浮点类型
				if (dbNum > Long.MAX_VALUE) {
					return dbNum;
				}

				// 判断数字是否为整数值
				long lngNum = (long) dbNum;
				if (dbNum == lngNum) {
					return lngNum;
				} else {
					return dbNum;
				}

			case BOOLEAN:
				return in.nextBoolean();

			case NULL:
				in.nextNull();
				return null;

			default:
				throw new IllegalStateException();
			}
		}

		@Override
		public void write(JsonWriter out, Object value) throws IOException {
			// 序列化无需实现
		}

	}
	
	public static void main(String[] args) {
		String aa="[{\"id\":226287,\"online_order_num\":7,\"online_order_amount\":1779200.00,\"real_name\":\"穆**\",\"id_card_no\":\"231********419\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"183*****719\"},{\"id\":2096384,\"online_order_num\":2,\"online_order_amount\":70000.00,\"real_name\":\"张**\",\"id_card_no\":\"370********582\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"136*****152\"},{\"id\":2106310,\"online_order_num\":0,\"online_order_amount\":420000.00,\"real_name\":\"薛**\",\"id_card_no\":\"231********820\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"187*****198\"},{\"id\":2259158,\"online_order_num\":1,\"online_order_amount\":1514900.00,\"real_name\":\"王**\",\"id_card_no\":\"370********524\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"150*****666\"},{\"id\":2289001,\"online_order_num\":13,\"online_order_amount\":769384.00,\"real_name\":\"王**\",\"id_card_no\":\"370********243\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"186*****803\"},{\"id\":2293168,\"online_order_num\":0,\"online_order_amount\":0.00,\"real_name\":\"娇**\",\"id_card_no\":\"370********220\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"186*****238\"},{\"id\":2295215,\"online_order_num\":1,\"online_order_amount\":143700.00,\"real_name\":\"石**\",\"id_card_no\":\"370********414\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"155*****328\"},{\"id\":2302044,\"online_order_num\":0,\"online_order_amount\":50000.00,\"real_name\":\"胥**\",\"id_card_no\":\"370********666\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"139*****789\"},{\"id\":2320687,\"online_order_num\":1,\"online_order_amount\":46000.00,\"real_name\":\"荆**\",\"id_card_no\":\"370********823\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"159*****568\"},{\"id\":2321247,\"online_order_num\":4,\"online_order_amount\":287000.00,\"real_name\":\"白**\",\"id_card_no\":\"370********413\",\"gender\":\"\",\"birth_month_day\":\"\",\"mobile\":\"158*****919\"}]";
		List<Map<String, Object>> list = GsonUtil.fromJson(aa,new TypeToken<List<Map<String, Object>>>() { });
		 System.out.println(list.toString());
	}

}
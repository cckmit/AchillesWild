package com.achilles.wild.server.tool.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.achilles.wild.server.entity.info.Citizen;

public class Test {

	public static void main(String[] args) {
//		for(int m=0;m<10000000;m++){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("b", "3");
		mapList.add(map);
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("a", "d");
		mapList.add(map1);
//		System.out.println(mapList);
		Collections.reverse(mapList);
		System.out.println(new Citizen());
			System.out.println(new Citizen().toString());
			System.out.println(mapList.hashCode());
			int m=0;
			Citizen c =	new Citizen();
			System.out.println(c.hashCode());
			c.setIdNo("er");
			System.out.println(c.hashCode());
//			Object o=null;
//		}
			int k=4;
			System.out.println(k);
	}
	
	private  void ch(int m) {
		int k;
		m=3;
	}

}

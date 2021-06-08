package com.achilles.wild.server.tool.bean;


import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import jodd.util.Base64;

public class BeanUtil {

    
    public static void main(String[] args) {

		System.out.println(ObjectSizeCalculator.getObjectSize(new Base64[9]));
    }


	/**
	 * getObjectSize
	 *
	 * @param obj
	 * @return
	 */
	public static long getObjectSize(Object obj){
		return ObjectSizeCalculator.getObjectSize(obj);
	}
    
}

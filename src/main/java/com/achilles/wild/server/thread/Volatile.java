package com.achilles.wild.server.thread;

public class Volatile {
	
	private int  m = 0;
	
	private static int  n = 100;
	
	public void add() {
		System.out.println("add start >>>>>>>>>>"+Thread.currentThread().getName()+"   m="+m);
		m++;
		System.out.println("add end ***********  "+Thread.currentThread().getName()+"   m="+m);
	}
	
	public int get() {
		return m;
	}
	
	public static void incr() {
		System.out.println("incr start >>>>>>>>>>"+Thread.currentThread().getName()+"   n="+n);
		n--;
		System.out.println("incr end ***********  "+Thread.currentThread().getName()+"   n="+n);
	}
	
	public static int getN() {
		return n;
	}
	

}

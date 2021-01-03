package com.achilles.wild.server.thread;


import org.junit.Test;

public class VolatileTest {
	
	@Test
	public void test1() {
		Volatile vol = new Volatile();
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread( new Runnable() { public void run() { vol.add(); } } );
			thread.start();
		}
		
		 try {
			Thread.sleep(1200L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(vol.get());
	}
	
	@Test
	public void test2() {
		
		for (int i = 0; i < 100; i++) {
			Volatile vol = new Volatile();
			Thread thread = new Thread( new Runnable() { public void run() { vol.incr(); } } );
			thread.start();
		}
		
		 try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Volatile.getN());
	}

}

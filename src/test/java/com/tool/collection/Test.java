package com.tool.collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;


public class Test {

    public static void main(String[] args) {
//        List list = new ArrayList();
//        for (int i = 0; i <1 ; i++) {
//                    list.add("feg"+i);
//            Map map map= new ConcurrentHashMap();
//            map.put("","");
//            list.remove(i);i\
//        }


        StringBuffer sb =  new StringBuffer();
        sb.append("").toString();

//        new Integer().toString();toString
//       new Object()Object.equals();
//       new Long().equals()
//        StringBuilder sbu = new StringBuilder();
//        sbu.append("").toString();

        String s2= new String("helloworld");
        String s1="helloworld";

        String s3= new String("helloworld");

//        System.out.println(s1.toString());

        System.out.println(s1==s2);
        System.out.println(s1.equals(s2));
//new Object().equals(34);
    }

    @org.junit.Test
    public void test1(){
//        Long s1=new Long(23);
//        Long s2= 23L;

        String s1="hello world";
        String s2="hello "+"world";
        System.out.println(s1==s2);

        AtomicInteger a = new AtomicInteger();
        a.incrementAndGet();
//        a.compareAndSet();

        Map<String, String> concurrentHashMap  = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put("","");

        System.out.println(s1.equals(s2));
    }
    
    @org.junit.Test
    public void test2(){
    	LongAdder addr = new LongAdder();
    	addr.add(23L);
    	
    	for (;;) { 
    		System.out.println("888888888888888");
    	}
    }
    
    @org.junit.Test
    public void test3(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("Achilles");
    	System.out.println(sb);
    }
    
//    @org.junit.Test
//    public void test4() throws IOException{
//    	Integer p = Integer.valueOf(12);
//    	new Integer(3);
////    	Map<String,Object> map = new HashMap<String, Object>();
////    	map.put("a", 23);
////    	map.put("A", 343);
////    	System.out.println(map);
//    	char m='a';
//
//    	System.out.println(m*34);
//    	System.out.println("aa".hashCode());
//    	System.out.println("A".hashCode());
//    	new Object().hashCode();
//
//    	ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181",
//                5000,
//                null);
//    	//zookeeper.create(path, data, acl, createMode)
//    }
//
    
    @org.junit.Test
    public void test5(){
    	String sre1 = "a3436363g343h3hm3phm3pmh3h3p";
    	String sre2 = "a3436363g343h3hm3phm3pmh3h3p";
    	String sre3 = new String("a3436363g343h3hm3phm3pmh3h3p");
    	System.out.println(sre1==sre2);
    	System.out.println(sre1.equals(sre3));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
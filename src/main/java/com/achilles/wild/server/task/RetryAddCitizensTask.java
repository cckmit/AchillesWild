package com.achilles.wild.server.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.achilles.wild.server.tool.ThreadUtil;
import com.alibaba.fastjson.JSON;

import com.achilles.wild.server.entity.info.Citizen;
import com.google.common.collect.Lists;
import com.achilles.wild.server.manager.CitizenManager;
import com.achilles.wild.server.service.impl.CitizenService2Impl;
import com.achilles.wild.server.service.impl.CitizenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class RetryAddCitizensTask {

    private final static Logger LOG = LoggerFactory.getLogger(RetryAddCitizensTask.class);

    private static ExecutorService poolExecutor = ThreadUtil.getExecutor(2,9,10);

   // volatile CitizenManager citizenManagerV= citizenManager;

    /**
     * 重试方法
     * @return
     */
    public static void addCitizenRunnable(CitizenManager citizenManager, DataSourceTransactionManager transactionManager,List<Citizen> list)  {

        CitizenService2Impl citizenService = new CitizenService2Impl();
        citizenService.setCitizenList(list);
        citizenService.setCitizenManager(citizenManager);
        citizenService.setTransactionManager(transactionManager);

        //poolExecutor.submit(citizenService);

        poolExecutor.execute(citizenService);

    }

    /**
     * 多线程执行
     * @param citizenManager
     * @param transactionManager
     * @param list
     * @return
     */
    public static int addCitizenTask(CitizenManager citizenManager, DataSourceTransactionManager transactionManager,List<Citizen> list)  {

        List<List<Citizen>> listList = Lists.partition(list, 1000);

        List<CitizenServiceImpl> serviceList = new ArrayList<>();
        for (int m=0;m<listList.size();m++){
            CitizenServiceImpl citizenService = new CitizenServiceImpl();
            citizenService.setM(m);
            citizenService.setCitizenList(listList.get(m));
            citizenService.setCitizenManager(citizenManager);
            citizenService.setTransactionManager(transactionManager);
            serviceList.add(citizenService);
        }

        List<Future<Integer>> futures = serviceList.stream().map(citizenService->poolExecutor.submit(citizenService)).collect(Collectors.toList());

        List<Integer> resultList = futures.stream().map(it -> {
            try {
                return it.get(30000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }).collect(Collectors.toList());

        LOG.info(" ---------------resultList-------------------- "+resultList);
        LOG.info(" ---------------resultList-----size----------- "+resultList.size());
        resultList = resultList.stream().distinct().collect(Collectors.toList());
        LOG.info(" ---------------resultList-----after distinct---------- "+resultList);

        LOG.info("before close thread pool:  "+ JSON.toJSONString(poolExecutor));
        //执行此函数后线程池不再接收新任务，并等待所有任务执行完毕后销毁线程。此函数不会等待销毁完毕
        poolExecutor.shutdown();
        LOG.info("after  close thread pool:  "+ JSON.toJSONString(poolExecutor));

        //立即结束所有线程，不管是否正在运行，返回未执行完毕的任务列表
        //System.out.println(" -----------------------------  "+executor.shutdownNow());
        //System.out.println("after  close thread pool:  "+ JSON.toJSONString(poolExecutor));

        if (resultList.size()!=1 || resultList.get(0)!=1){
            return 0;
        }
        return 1;
    }


    public static int retryTask(String str){
        LOG.info("---------------"+str);

        return 0;
    }

}

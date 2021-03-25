package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.business.dao.common.ConfigParamsDao;
import com.achilles.wild.server.entity.common.ConfigParams;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.annotation.Resource;

public class ConfigConfigParamsDaoTest extends StarterApplicationTests {

    @Resource
    private ConfigParamsDao configParamsDao;

    @Test
    public void insert(){

        ConfigParams configParams = new ConfigParams();
        configParams.setKey("achilles"+System.currentTimeMillis());
        configParams.setVal("wild");
        configParams.setDescription("desc");
        int count = configParamsDao.insert(configParams);

        ConfigParams configParams1 = configParamsDao.selectByKey(configParams.getKey());



        System.out.println(JSON.toJSONString(configParams1));

    }


    @Test
    public void updateByCode(){


       int count = configParamsDao.updateByKey("wer","阿基里斯1");

        System.out.println();
    }

}

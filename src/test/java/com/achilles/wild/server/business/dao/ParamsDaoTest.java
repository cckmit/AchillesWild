package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.SpringbootApplicationTests;
import com.achilles.wild.server.business.dao.info.ParamsDao;
import com.achilles.wild.server.entity.common.Params;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.annotation.Resource;

public class ParamsDaoTest  extends SpringbootApplicationTests {

    @Resource
    private ParamsDao paramsDao;

    @Test
    public void insert(){

        Params params = new Params();
        params.setKey("achilles"+System.currentTimeMillis());
        params.setVal("wild");
        params.setDescription("desc");
        int count = paramsDao.insert(params);

        Params params1 = paramsDao.selectByKey(params.getKey());



        System.out.println(JSON.toJSONString(params1));

    }


    @Test
    public void updateByCode(){


       int count = paramsDao.updateByKey("wer","阿基里斯1");

        System.out.println();
    }

}

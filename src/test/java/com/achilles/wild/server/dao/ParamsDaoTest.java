package com.achilles.wild.server.dao;

import com.achilles.wild.server.SpringbootApplicationTests;
import com.achilles.wild.server.dao.info.ParamsDao;
import com.achilles.wild.server.entity.info.Params;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.annotation.Resource;

public class ParamsDaoTest  extends SpringbootApplicationTests {

    @Resource
    private ParamsDao paramsDao;

    @Test
    public void insert(){

        Params params = new Params();
        params.setCode("achilles"+System.currentTimeMillis());
        params.setVal("wild取值");
        int count = paramsDao.insert(params);

        Params params1 = paramsDao.selectByCode(params.getCode());



        System.out.println(JSON.toJSONString(params1));

    }


    @Test
    public void updateByCode(){


       int count = paramsDao.updateByCode("wer","阿基里斯1");

        System.out.println();
    }

}

package com.achilles.wild.server.tool.data;

import java.util.List;

import javax.annotation.Resource;

import com.achilles.wild.server.design.proxy.cglib.CglibInterceptor;
import com.achilles.wild.server.design.proxy.cglib.ServiceClient;
import com.achilles.wild.server.design.proxy.jdk.JavaProxyInvocationHandler;
import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.model.query.CitizenQuery;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.service.CitizenService;
import com.achilles.wild.server.tool.BaseSpringJUnitTest;
import org.junit.Test;

public class CitizenExtTest  extends BaseSpringJUnitTest {

    @Resource
    private List<CitizenService> citizenServiceList;

    @Resource
    private CitizenService citizenService;

    @Resource
    private CglibInterceptor cglibInterceptor;

    @Test
    public void getCitizensProxyCgLIB(){

        ServiceClient serviceClientProxy = (ServiceClient) cglibInterceptor.newProxyInstance(ServiceClient.class);
        serviceClientProxy.doIt();

        System.out.println();
    }

    @Test
    public void getCitizensProxyJDK(){

        JavaProxyInvocationHandler proxyInvocationHandler = new JavaProxyInvocationHandler(citizenService);
        CitizenService citizenServiceProxy = (CitizenService) proxyInvocationHandler.newProxyInstance();

        DataResult<List<Citizen>> data = citizenServiceProxy.getCitizens(new CitizenQuery());
        System.out.println();
    }

    @Test
    public void getCitizens(){
        DataResult<List<Citizen>> data = citizenServiceList.get(0).getCitizens(new CitizenQuery());
        System.out.println();
    }
}

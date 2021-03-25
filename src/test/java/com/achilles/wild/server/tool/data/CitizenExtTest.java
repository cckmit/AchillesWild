package com.achilles.wild.server.tool.data;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.other.design.proxy.cglib.CglibInterceptor;
import com.achilles.wild.server.other.design.proxy.cglib.ServiceClient;
import com.achilles.wild.server.other.design.proxy.jdk.JavaProxyInvocationHandler;
import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.model.query.info.CitizenQuery;
import com.achilles.wild.server.model.response.PageResult;
import com.achilles.wild.server.business.service.info.CitizenService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class CitizenExtTest  extends StarterApplicationTests {

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

        PageResult<List<Citizen>> data = citizenServiceProxy.getCitizens(new CitizenQuery());
        System.out.println();
    }

    @Test
    public void getCitizens(){
        PageResult<List<Citizen>> data = citizenServiceList.get(0).getCitizens(new CitizenQuery());
        System.out.println();
    }
}

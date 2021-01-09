package com.achilles.wild.server.controller;

import com.achilles.wild.server.config.ConfigComplex;
import com.achilles.wild.server.config.ConfigProperties;
import com.achilles.wild.server.config.ConfigProperties1;
import com.achilles.wild.server.config.ConfigProperties2;
import com.achilles.wild.server.entity.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/demo",produces = {"application/json;charset=UTF-8"})
public class DemoController {

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    ConfigProperties1 configProperties1;

    @Autowired
    ConfigProperties2 configProperties2;

    @Autowired
    ConfigComplex configComplex;

    @Autowired
    Environment environment;

    @GetMapping(path = "/{id}")
    public Long getConfig(@PathVariable("id") Long id,
                             @RequestParam(name="name",defaultValue = "Achilles") String name,
                             @RequestParam(name="limit",defaultValue = "10") Integer limit,
                             @RequestHeader(name = "traceId",required = false) String traceId){

        System.out.println("=====configProperties ===="+configProperties.getUsername());
        System.out.println("=====configProperties1 ===="+configProperties1.getName());
        System.out.println("=====configProperties2 ===="+configProperties2.getHouse());
        System.out.println("=====configComplex ===="+configComplex.mysqlConfig().getUrl());
        System.out.println("=====environment ==house=="+environment.getProperty("house"));
        System.out.println("=====environment ==spring.datasource.url=="+environment.getProperty("spring.datasource.url"));

        Account account = new Account();
        account.setId(id);
        account.setAccountCode(traceId);
        return id;
    }

    @PostMapping(path = "/save")
    public Account save(@RequestBody(required = true) Account account){

        System.out.println("=====params ==========save========account:"+account);

        return account;
    }
}

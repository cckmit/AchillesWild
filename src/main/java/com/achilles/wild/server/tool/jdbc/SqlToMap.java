package com.achilles.wild.server.tool.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class SqlToMap {
    public void generator() throws Exception {

        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        //Configuration config = cp.parseConfiguration(this.getClass().getClassLoader().getResourceAsStream("generatorConfig_for_oracle��wmsserv��.xml"));
        String xmlName="generatorConfig.xml";
        //String xmlName = "/Users/achilleswild/Documents/Work/tools/src/main/resources/generatorConfig.xml";  //mysql 8
        Configuration config = cp.parseConfiguration(this.getClass().getClassLoader().getResourceAsStream(xmlName));

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
            myBatisGenerator.generate(null);
            System.out.println("���򹤳��������ݳɹ�!!!");

        } catch (InvalidConfigurationException e) {
            //ʧ��
        }


    }

    // ִ��main���������ɴ���
    public static void main(String[] args) {
        try {
            SqlToMap generatorSqlmap = new SqlToMap();
            generatorSqlmap.generator();
            System.out.println("---------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

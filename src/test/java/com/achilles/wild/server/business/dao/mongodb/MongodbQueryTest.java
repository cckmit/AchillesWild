package com.achilles.wild.server.business.dao.mongodb;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.entity.user.User;
import com.achilles.wild.server.tool.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MongodbQueryTest extends StarterApplicationTests {


    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void find() {

        List<User> users = mongoTemplate.findAll(User.class);
        users.forEach(user -> {
            System.out.println(JsonUtil.toJson(user));
        });

    }

    @Test
    public void findById() {

        User user = mongoTemplate.findById(1,User.class);
        System.out.println(JsonUtil.toJson(user));

    }

    @Test
    public void findOne() {

        User user = mongoTemplate.findOne(new Query(Criteria.where("id").is(1L)), User.class);
        System.out.println(JsonUtil.toJson(user));

    }

    @Test
    public void findList() {

        List<User> users = mongoTemplate.find(new Query(Criteria.where("email").is("54434@qq.com")).limit(1), User.class);
        System.out.println(JsonUtil.toJson(users));

    }
}

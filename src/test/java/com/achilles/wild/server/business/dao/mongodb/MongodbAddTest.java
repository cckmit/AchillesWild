package com.achilles.wild.server.business.dao.mongodb;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.entity.user.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongodbAddTest extends StarterApplicationTests {


    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void add() {

        User user = new User();
        user.setId(2L);
        user.setMobile("18701514747");
        user.setEmail("54434@qq.com");
        user = mongoTemplate.insert(user);
        user.setId(3L);
        user.setMobile("18001430088");
        user.setEmail("54434@qq.com");
        user = mongoTemplate.insert(user);
        System.out.println("SUCCESS ！ user : " + user);

    }


}

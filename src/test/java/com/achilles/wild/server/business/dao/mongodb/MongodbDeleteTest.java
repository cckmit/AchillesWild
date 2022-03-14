package com.achilles.wild.server.business.dao.mongodb;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.entity.user.User;
import com.mongodb.client.result.DeleteResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongodbDeleteTest extends StarterApplicationTests {


    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void delete() {

        User user = new User();
        user.setId(2L);
        DeleteResult result = mongoTemplate.remove(user);
        System.out.println();
    }


}

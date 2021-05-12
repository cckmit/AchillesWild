package com.achilles.wild.server.business;

import com.achilles.wild.server.StarterApplicationTests;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StringEncryptorTest extends StarterApplicationTests {

    @Autowired
    StringEncryptor encryptor;

    @Test
    public void getPass() {
        String root = encryptor.encrypt("root");
        System.out.println("encrypt  root     :     "+root);

        root = encryptor.decrypt(root);
        System.out.println("decrypt  root     :     "+root);
    }
}

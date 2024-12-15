package com.fei.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PassWordEncodingTest
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void test01()
    {
        String password="123456";
        String encode = passwordEncoder.encode(password);
        System.out.println(encode);
    }
     @Test
    public void test02()
    {
        String password="123456";
        String encode = "$2a$10$vglGaCjXPE0R0v7R0Ib82eUcwSnln5hrsBkJaBe9cV0uOrB0XxcMu";
        System.out.println(passwordEncoder.matches(password,encode));
    }

}

package com.fei.stock.pojo;

import com.fei.stock.utils.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordTest
{
    @Autowired
    private IdWorker idWorker;
    @Test
    public void fun1()
    {
        System.out.println(idWorker);
    }
}

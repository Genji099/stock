package com.fei.stock;

import com.fei.stock.mapper.StockBusinessMapper;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class MapperTest
{
    @Autowired
    private StockBusinessMapper stockBusinessMapper;
    @Test
    public void fun1()
    {
        List<String> allSockCode =stockBusinessMapper.getAllSockCode();
        System.out.println(allSockCode);
        allSockCode=allSockCode.stream().map(code->code.startsWith("6")?"sh"+code:"sz"+code)
        .collect(Collectors.toList());

        Lists.partition(allSockCode,10).forEach(System.out::println);
    }
}

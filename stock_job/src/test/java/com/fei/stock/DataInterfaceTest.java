package com.fei.stock;

import com.fei.stock.service.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataInterfaceTest
{
    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    @Test
    public void fun1()
    {
        stockTimerTaskService.getInnerMarketInfo();
    }
    @Test
    public void fun2() throws InterruptedException
    {
        stockTimerTaskService.getStockRtIndex();
        Thread.sleep(5000);
    }
    @Test
    public void fun3()
    {
        stockTimerTaskService.getStockBlockInfo();
    }
    @Test
    public void fun4()
    {
        stockTimerTaskService.getOutMarketInfo();
    }

}

package com.fei.stock.job;

import com.fei.stock.service.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockJob
{
    @Autowired
    private StockTimerTaskService stockTimerTaskService;


    @XxlJob("sotck-xxl-test")
    public void myJobTest() throws Exception {
        String string = DateTime.now().toString("yyyy-MM-dd : hh:mm:ss");
        System.out.println(string);
    }

    /**
     * 获取大盘信息
     * @throws Exception
     */
    @XxlJob("getInnerMarketInfo")
    public void getInnerMarket() throws Exception {
        stockTimerTaskService.getInnerMarketInfo();
    }

    /**
     * 获取个股信息
     * @throws Exception
     */
    @XxlJob("getStockRtIndex")
    public void getStockRtIndex() throws Exception {
        stockTimerTaskService.getStockRtIndex();
    }

    /**
     * 获取板块信息
     * @throws Exception
     */
    @XxlJob("getStockBlockInfo")
    public void getStockBlockInfo() throws Exception {
        stockTimerTaskService.getStockBlockInfo();
    }
    @XxlJob("getOutMarketInfo")
    public void getOutMarketInfo() throws Exception {
        stockTimerTaskService.getOutMarketInfo();
    }


}

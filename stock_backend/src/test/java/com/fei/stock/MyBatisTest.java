package com.fei.stock;

import com.fei.stock.mapper.StockBlockRtInfoMapper;
import com.fei.stock.mapper.StockRtInfoMapper;
import com.fei.stock.pojo.domain.Stock4EvrDayDomain;
import com.fei.stock.pojo.domain.StockBlockDomain;
import com.fei.stock.pojo.domain.StockUpdownDomain;
import com.fei.stock.service.impl.StockServiceImpl;
import com.fei.stock.utils.DateTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


@SpringBootTest
public class MyBatisTest
{
//    @Autowired
//    private StockServiceImpl stockService;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;
    @Test
    public void getBlockInfoTest()
    {
        Date date = DateTime.parse("2021-12-21 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        List<StockBlockDomain> blockInfo = stockBlockRtInfoMapper.getBlockInfo(date);
        System.out.println(blockInfo);

    }
    @Test
    public void get4Desc()
    {
        Date date = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        List<StockUpdownDomain> stockInfo4Desc = stockRtInfoMapper.getStockInfo4Desc(date);
        System.out.println(stockInfo4Desc);

    }
    @Test
    public void fun4()
    {
        //获取日k线的时间范围
        //截至时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        endDateTime=DateTime.parse("2022-01-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date endDate = endDateTime.toDate();
        //起始时间
        DateTime startDateTime = endDateTime.minusDays(14);
        startDateTime=DateTime.parse("2022-01-01 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date startDate = startDateTime.toDate();
        List<Date> data=stockRtInfoMapper.getDayLineDateList(startDate,endDate,"600021");
        System.out.println("sdf");
    }
    @Test
    public void fun5()
    {
        //获取日k线的时间范围
        //截至时间
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        endDateTime=DateTime.parse("2022-01-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date endDate = endDateTime.toDate();
        //起始时间
        DateTime startDateTime = endDateTime.minusDays(14);
        startDateTime=DateTime.parse("2022-01-01 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date startDate = startDateTime.toDate();
        List<Date> dayLineDateList = stockRtInfoMapper.getDayLineDateList(startDate, endDate,"600021");
        List<Stock4EvrDayDomain>data=stockRtInfoMapper.getStockDayKLinByList(dayLineDateList,"600021");
        System.out.println("sdf");
    }

}

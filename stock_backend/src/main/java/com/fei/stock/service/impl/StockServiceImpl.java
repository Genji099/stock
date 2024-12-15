package com.fei.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.fei.stock.mapper.StockBlockRtInfoMapper;
import com.fei.stock.mapper.StockMarketIndexInfoMapper;
import com.fei.stock.mapper.StockRtInfoMapper;
import com.fei.stock.pojo.domain.*;
import com.fei.stock.pojo.vo.StockInfoConfig;
import com.fei.stock.service.StockService;
import com.fei.stock.utils.DateTimeUtil;
import com.fei.stock.vo.req.PageResult;
import com.fei.stock.vo.resp.Rest;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

//股票接口的实现
@Slf4j
@Service
public class StockServiceImpl implements StockService
{
    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;
    /**
     * 注入本地缓存
     */
    @Autowired
    private Cache<String,Object> caffeineCache;

    @Override
    public Rest<List<InnerMarketDomain>> getInnerMarketInfo()
    {
        //默认从本地缓存加载,如果缓存没有,则从数据库加载
        Rest<List<InnerMarketDomain>> result = (Rest<List<InnerMarketDomain>>) caffeineCache.get("InnerMarketKey", key -> {
            DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
            Date curDate = curDateTime.toDate();
            //当前没有实现采集数据功能,先使用假数据
            curDate = DateTime.parse("2022-07-07 14:52:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
            //获取大盘编码集合
            List<String> mCodes = stockInfoConfig.getInner();
            //调用mapper查询数据
            List<InnerMarketDomain> date =
                    stockMarketIndexInfoMapper.getMarketInfo(curDate, mCodes);

            return Rest.ok(date);
        });
        //获取股最新交易时间
        return  result;
    }

    @Override
    public Rest<List<StockBlockDomain>> getStockBlockInfo()
    {
        //获取股最新交易时间
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curDate = curDateTime.toDate();
        //当前没有实现采集数据功能,先使用假数据
        curDate = DateTime.parse("2021-12-21 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //List<StockBlockDomain>
        List<StockBlockDomain> blockInfo = stockBlockRtInfoMapper.getBlockInfo(curDate);
        return Rest.ok(blockInfo);
    }

    @Override
    public Rest<PageResult<StockUpdownDomain>> getStockInfoByPage(Integer page, Integer pageSize)
    {
        //获取股最新交易时间
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curDate = curDateTime.toDate();
        //当前没有实现采集数据功能,先使用假数据
        curDate = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //设置分页参数
        PageHelper.startPage(page, pageSize);

        List<StockUpdownDomain> pageData = stockRtInfoMapper.getStockInfoByTime(curDate);
        PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(pageData);
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);

        return Rest.ok(pageResult);
    }

    @Override
    public Rest<List<StockUpdownDomain>> getStockInfo4Desc()
    {
        //获取股最新交易时间
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curDate = curDateTime.toDate();
        //当前没有实现采集数据功能,先使用假数据
        curDate = DateTime.parse("2021-12-30 09:42:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<StockUpdownDomain> stockList = stockRtInfoMapper.getStockInfoByTime(curDate);
        return Rest.ok(stockList);
    }

    @Override
    public Rest<Map<String, List>> getStockUpDownCount()
    {
        //获取股最新交易时间
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());


        //当前没有实现采集数据功能,先使用假数据
        curDateTime = DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        //获取最新交易点对应的开盘时间
        Date curDate = curDateTime.toDate();
        Date openDate = DateTimeUtil.getOpenDate(curDateTime).toDate();
        //2.查询涨停数据
        //约定mapper中flag入参： 1-》涨停数据 0：跌停
        List<Map> upCounts = stockRtInfoMapper.getStockUpdownCount(openDate, curDate, 1);
        //3.查询跌停数据
        List<Map> dwCounts = stockRtInfoMapper.getStockUpdownCount(openDate, curDate, 0);
        //4.组装数据
        HashMap<String, List> mapInfo = new HashMap<>();
        mapInfo.put("upList", upCounts);
        mapInfo.put("downList", dwCounts);
        //5.返回结果
        return Rest.ok(mapInfo);
    }

    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize)
    {
        //获取分页数据
        Rest<PageResult<StockUpdownDomain>> infoByPage = this.getStockInfoByPage(page, pageSize);
        List<StockUpdownDomain> domainList = infoByPage.getData().getRows();
        //将数据导出到excel中
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = null;
        try
        {
            fileName = URLEncoder.encode("股票信息表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), StockUpdownDomain.class).sheet("股票涨幅信息").doWrite(domainList);
        } catch (IOException e)
        {
            DateTime now = DateTime.now();
            log.error("{}: {}", now, e.getMessage());
        }

    }

    @Override
    public Rest<Map<String, List<Map>>> getComparedStockTradeAmt()
    {
        //获取x日最新股票交易日期的范围
        //先获取截至时间
        DateTime xEndDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //先用假数据
        xEndDateTime =DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date xEndDate = xEndDateTime.toDate();
        Date xStartDate = DateTimeUtil.getOpenDate(xEndDateTime).toDate();
        DateTime preXEndDateTime = DateTimeUtil.getPreviousTradingDay(xEndDateTime);
        preXEndDateTime =DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

        Date preXEndDate = preXEndDateTime.toDate();
        //上一天的开盘时间
        Date xPreStartDate = DateTimeUtil.getOpenDate(preXEndDateTime).toDate();
        //调用mapper
        List<Map> xData=stockMarketIndexInfoMapper.getSumAmtInfo(xStartDate,xEndDate,stockInfoConfig.getInner());
        List<Map> preData=stockMarketIndexInfoMapper.getSumAmtInfo(xPreStartDate,preXEndDate,stockInfoConfig.getInner());
        //封装数据
        HashMap<String,List<Map>> data=new HashMap<>();
        data.put("amtList",xData);
        data.put("yesAmtList",preData);
        return  Rest.ok(data);
    }

    @Override
    public Rest<Map> getIncreaseRangeInfo()
    {
        DateTime dateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //先用假数据
        dateTime =DateTime.parse("2022-01-06 09:55:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date curDate = dateTime.toDate();
        Map<String,Object>data=new HashMap<>();
        List<Map>rangeData=new ArrayList<>();
        List<Map>mapperData=stockRtInfoMapper.getIncreaseRangeInfoByDate(curDate);
        List<String> upDownRange = stockInfoConfig.getUpDownRange();

        for (String title : upDownRange)
        {
            Map tmp=null;
            for (Map i : mapperData)
            {
                if(i.containsValue(title))
                {
                    tmp=i;
                    break;
                }
            }
            if(tmp==null)
            {
                tmp=new HashMap();
                tmp.put("count",0);
                tmp.put("title",title);
            }
            rangeData.add(tmp);
        }
        data.put("time",dateTime.toString("YYYY-MM-DD HH:mm:ss"));
        data.put("infos",rangeData);
        return  Rest.ok(data);
    }

    /**
     * 指定股票t日的分时数据
     * @param stockCode
     * @return
     */
    @Override
    public Rest<List<Stock4MinuteDomain>> stockScreenTimeSharing(String stockCode)
    {
        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        endDateTime =DateTime.parse("2021-12-30 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        //结束时间
        Date endDate = endDateTime.toDate();
        DateTime openDateTime = DateTimeUtil.getOpenDate(endDateTime);
        Date openDate = openDateTime.toDate();
        List<Stock4MinuteDomain>data=stockRtInfoMapper.getStockInfoByCodeAndDate(openDate,endDate,stockCode);
        return Rest.ok(data);

    }

    /**
     * 查询指定股票的日k线
     * @param stockCode 股票代码
     * @return
     */
    @Override
    public Rest<List<Stock4EvrDayDomain>> getStockDayKLin(String stockCode)
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
        List<Date> dayLineDateList = stockRtInfoMapper.getDayLineDateList(startDate, endDate,stockCode);
        List<Stock4EvrDayDomain>data=stockRtInfoMapper.getStockDayKLinByList(dayLineDateList,stockCode);
        //List<Stock4EvrDayDomain>data=stockRtInfoMapper.getStockDayKLin(startDate,endDate,stockCode);
        return Rest.ok(data);
    }
//    @Override
//    public List<Date>getDayLineDateList()
//    {
//        //获取日k线的时间范围
//        //截至时间
//        DateTime endDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
//        endDateTime=DateTime.parse("2022-01-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
//        Date endDate = endDateTime.toDate();
//        //起始时间
//        DateTime startDateTime = endDateTime.minusDays(14);
//        startDateTime=DateTime.parse("2022-01-01 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
//        Date startDate = startDateTime.toDate();
//        List<Date> data=stockRtInfoMapper.getDayLineDateList(startDate,endDate);
//        return data;
//    }
}

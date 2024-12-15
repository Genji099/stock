package com.fei.stock.service;

import com.fei.stock.pojo.domain.*;
import com.fei.stock.vo.req.PageResult;
import com.fei.stock.vo.resp.Rest;
import io.swagger.annotations.ApiModel;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

//股票接口
@ApiModel(description = "股票接口")
public interface StockService
{

    Rest<List<InnerMarketDomain>> getInnerMarketInfo();

    Rest<List<StockBlockDomain>> getStockBlockInfo();

    Rest<PageResult<StockUpdownDomain>> getStockInfoByPage(Integer page, Integer pageSize);

    Rest<List<StockUpdownDomain>> getStockInfo4Desc();

    Rest<Map<String, List>> getStockUpDownCount();

    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);

    Rest<Map<String, List<Map>>> getComparedStockTradeAmt();

    Rest<Map> getIncreaseRangeInfo();

    Rest<List<Stock4MinuteDomain>> stockScreenTimeSharing(String stockCode);

    Rest<List<Stock4EvrDayDomain>> getStockDayKLin(String stockCode);

    //List<Date>getDayLineDateList();
}

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

    /**
     * 模糊查找股票数据
     * @param searchStr 股票代码,不可以是文字
     * @return
     */
    Rest<List<Map>> searchStock(String searchStr);

    /**
     * 外盘指数行情数据查询，根据时间和大盘点数降序排序取前4
     * @return
     */
    Rest<List<OuterMarketDomain>> getOuterMarketInfo();

    /**
     * 个股主营业务查询接口
     * @param code
     * @return
     */
    Rest<StockBusinessInfo> getStockBusinessInfo(String code);

    /**
     * 获取周k线接口
     * @param code
     * @return
     */
    Rest<List<WeekLineDomain>> getWeekLineInfo(String code);

    Rest<StockTimeSharingDomain> getStockTimeSharingInfo(String code);

    /**
     * 个股交易流水行情数据查询--查询最新交易流水，按照交易时间降序取前10
     * @param code
     * @return
     */
    Rest<List<StockBillDomain>> getStockBillInfo(String code);

    //List<Date>getDayLineDateList();
}

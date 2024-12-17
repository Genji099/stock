package com.fei.stock.controller;

import com.fei.stock.pojo.domain.*;
import com.fei.stock.service.StockService;
import com.fei.stock.vo.req.PageResult;
import com.fei.stock.vo.resp.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 定义股票相关接口
 */
@Api(value = "/api/quot", tags = {"定义股票相关接口"})
@ResponseBody
@Controller
@RequestMapping("/api/quot")
public class StockController
{

    @Autowired
    private StockService stockService;



    //获取国内大盘最新的数据
    @ApiOperation(value = "获取国内大盘最新的数据", notes = "获取国内大盘最新的数据", httpMethod = "GET")
    @GetMapping("index/all")
    public Rest<List<InnerMarketDomain>> getInnerMarketInfo()
    {
        return stockService.getInnerMarketInfo();
    }

    @ApiOperation(value = "查询沪深两市最新的板块行情数据", notes = "并按照交易金额降序排序展示前10条记录", httpMethod = "GET")
    @GetMapping("sector/all")
    public Rest<List<StockBlockDomain>> getStockBlockInfo()
    {
        return stockService.getStockBlockInfo();
    }

    /**
     *
     * @param page 当前页
     * @param pageSize 每页大小
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页大小")
    })
    @ApiOperation(value = "股票涨幅排行", notes = "股票涨幅排行", httpMethod = "GET")
    @GetMapping("/stock/all")
    public Rest<PageResult<StockUpdownDomain>> getStockInfoByPage(@RequestParam(value = "page",required = false,defaultValue = "1") Integer page, @RequestParam(value = "pageSize",required = false,defaultValue = "20")Integer pageSize)
    {
        return stockService.getStockInfoByPage(page,pageSize);
    }

    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/stock/increase")
    public Rest<List<StockUpdownDomain>> getStockInfo4Desc()
    {
        return stockService.getStockInfo4Desc();
    }

    /**
     * 每分钟涨跌停的数量
     * @return
     */
    @ApiOperation(value = "每分钟涨跌停的数量", notes = "每分钟涨跌停的数量", httpMethod = "GET")
    @GetMapping("/stock/updown/count")
    public Rest<Map<String,List>> getStockUpDownCount()
    {
        return stockService.getStockUpDownCount();
    }

    /**
     *  导出指定的股票信息
      * @param response
     * @param page
     * @param pageSize
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "")
    })
    @ApiOperation(value = "导出指定的股票信息", notes = "导出指定的股票信息", httpMethod = "GET")
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response,
                            @RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                            @RequestParam(value = "pageSize",required = false,defaultValue = "20")Integer pageSize)
    {
        stockService.stockExport(response,page,pageSize);
    }

    /**
     * 统计x和x-1日每分钟交易量的数据
     * @return
     */
    @ApiOperation(value = "统计x和x-1日每分钟交易量的数据", notes = "统计x和x-1日每分钟交易量的数据", httpMethod = "GET")
    @GetMapping("/stock/tradeAmt")
    public Rest<Map<String,List<Map>>> getComparedStockTradeAmt()
    {
        return stockService.getComparedStockTradeAmt();
    }
    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点
     */
    @ApiOperation(value = "查询当前时间下股票的涨跌幅度区间统计功能 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点", notes = "查询当前时间下股票的涨跌幅度区间统计功能 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点", httpMethod = "GET")
    @GetMapping("/stock/updown")
    public Rest<Map> getIncreaseRangeInfo(){
        return stockService.getIncreaseRangeInfo();
    }

    /**
     * 指定股票t日的分时数据
     * @param stockCode
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "stockCode", value = "", required = true)
    })
    @ApiOperation(value = "指定股票t日的分时数据", notes = "指定股票t日的分时数据", httpMethod = "GET")
    @GetMapping("/stock/screen/time-sharing")
    public Rest<List<Stock4MinuteDomain>> stockScreenTimeSharing(@RequestParam(value = "code",required = true) String stockCode){
        return stockService.stockScreenTimeSharing(stockCode);
    }

    /**
     * 查询指定股票的日k线
     * @param stockCode 股票代码
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "stockCode", value = "股票代码", required = true)
    })
    @ApiOperation(value = "查询指定股票的日k线", notes = "查询指定股票的日k线", httpMethod = "GET")
    @GetMapping("/stock/screen/dkline")
    public Rest<List<Stock4EvrDayDomain>> getStockDayKLin(@RequestParam(value = "code",required = true) String stockCode){
        return stockService.getStockDayKLin(stockCode);
    }

    /**
     * 根据股票代码模糊查找股票
     * @param searchStr
     * @return
     */
    @GetMapping("/stock/search")
    public Rest<List<Map>> searchStock(@RequestParam(value = "searchStr",required = true) String searchStr)
    {
        return stockService.searchStock(searchStr);
    }

    /**
     * 外盘指数行情数据查询，根据时间和大盘点数降序排序取前4
     * @return
     */
    @GetMapping("/external/index")
    public Rest<List<OuterMarketDomain>> getOuterMarketInfo()
    {
        return  stockService.getOuterMarketInfo();
    }

    /**
     * 获取公司业务信息
     * @param code 股票代码
     * @return
     */
    @GetMapping("/stock/describe")
    public Rest<StockBusinessInfo> getStockBusinessInfo(@RequestParam(value = "code",required = true) String code)
    {
        return  stockService.getStockBusinessInfo(code);
    }

    /**
     * 获取周k线
     * @param code 股票代码
     * @return
     */
    @GetMapping("/stock/screen/weekkline")
    public Rest<List<WeekLineDomain>> getWeekLineInfo(@RequestParam(value = "code",required = true) String code)
    {
        return stockService.getWeekLineInfo(code);
    }

    /**
     * 获取个股最新分时行情数据
     * @param code 股票代码
     * @return
     */
    @GetMapping("/stock/screen/second/detail")
    public Rest<StockTimeSharingDomain> getStockTimeSharingInfo(@RequestParam(value = "code") String code)
    {
        return stockService.getStockTimeSharingInfo(code);
    }

    @GetMapping("/stock/screen/second")
    public Rest<List<StockBillDomain>> getStockBillInfo(@RequestParam(value = "code") String code)
    {
        return stockService.getStockBillInfo(code);
    }
}

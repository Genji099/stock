package com.fei.stock.mapper;

import com.fei.stock.pojo.domain.*;
import com.fei.stock.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 59747
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2024-11-17 00:41:34
* @Entity com.fei.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    /**
     * 指定时间点的股票数据集合
     * @param curDate
     * @return
     */
    List<StockUpdownDomain> getStockInfoByTime(@Param("curData") Date curDate);

    /**
     * 获取振幅排名前四的数据
     * @param curDate
     * @return
     */
    List<StockUpdownDomain> getStockInfo4Desc(@Param("curData") Date curDate);

    /**
     * 统计指定范围内涨跌或者涨停的数量
     * @param openDate 开盘时间
     * @param curDate 当前时间
     * @param flag 标识符 1:涨停 0:跌停
     * @return
     */
    List<Map> getStockUpdownCount(@Param("openDate") Date openDate, @Param("curDate") Date curDate, @Param("flag") int flag);

    /**
     * 指定时间点下各个股票涨跌的数量
     * @param curDate
     * @return
     */
    List<Map> getIncreaseRangeInfoByDate(@Param("dataTime") Date curDate);

    /**
     * 指定股票t日的分时数据
     * @param openDate 开始时间
     * @param endDate  截至时间
     * @param stockCode 股票代码
     * @return
     */
    List<Stock4MinuteDomain> getStockInfoByCodeAndDate(@Param("openDate") Date openDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);

    /**
     * 查询指定股票的日k线
     * @param startDate
     * @param endDate
     * @param stockCode
     * @return
     */
    List<Stock4EvrDayDomain> getStockDayKLin(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);

    /**
     * 获取日k线的时间集合
     * @param startDate
     * @param endDate
     * @return
     */
    List<Date> getDayLineDateList(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);

    /**
     * 先查找时间集合,在通过时间集合查找日k线数据
     * @param dayLineDateList
     * @return
     */
    List<Stock4EvrDayDomain> getStockDayKLinByList(@Param("dayLineDate") List<Date> dayLineDateList, @Param("stockCode") String stockCode);

    /**
     * 批量插入个股数据
     * @param stockRtList
     * @return
     */
    int insertByList(@Param("list") List<StockRtInfo> stockRtList);

    /**
     * 获取周k线
     * @param startDate
     * @param endDate
     * @param code
     * @return
     */
    List<WeekLineDomain> getWeekLineInfo(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("code") String code);

    /**
     * 获取个股最新分时行情数据，主要包含：
     * 	开盘价、前收盘价、最新价、最高价、最低价、成交金额和成交量、交易时间信息;
     * @param endTime 结束时间
     * @param code 股票代码
     * @return
     */
    StockTimeSharingDomain getStockTimeSharingInfo(@Param("endTime") Date endTime, @Param("code") String code);

    /**
     * 个股交易流水行情数据查询--查询最新交易流水，按照交易时间降序取前10
     * @param endDate 最新时间
     * @param code
     * @return
     */
    List<StockBillDomain> getStockBillInfo(@Param("endDate") Date endDate, @Param("code") String code);
}

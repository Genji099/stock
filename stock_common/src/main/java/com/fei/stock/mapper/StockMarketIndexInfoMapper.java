package com.fei.stock.mapper;

import com.fei.stock.pojo.domain.InnerMarketDomain;
import com.fei.stock.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 59747
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2024-11-17 00:41:34
* @Entity com.fei.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     *根据指定时间点查询大盘数据
     * @param curDate 指定时间点
     * @param marketCodes 大盘编码集合
     * @return
     */
    List<InnerMarketDomain> getMarketInfo(@Param("curDate") Date curDate, @Param("marketCodes") List<String> marketCodes);

    /**
     * 指定时间内大盘成交量的流水信息
     * @param openDate 开始时间
     * @param endDate 截至时间
     * @param inner 大盘编码集合
     * @return
     */
    List<Map> getSumAmtInfo(@Param("openDate") Date openDate,@Param("endDate")Date endDate,@Param("marketCodes")List<String> inner);

    /**
     * 插入采集的大盘数据
     * @param list
     * @return
     */
    int insertByList(@Param("list") ArrayList<StockMarketIndexInfo> list);
}

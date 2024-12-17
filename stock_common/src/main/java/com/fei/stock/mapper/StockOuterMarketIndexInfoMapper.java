package com.fei.stock.mapper;

import com.fei.stock.pojo.domain.OuterMarketDomain;
import com.fei.stock.pojo.entity.StockOuterMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 59747
* @description 针对表【stock_outer_market_index_info(外盘详情信息表)】的数据库操作Mapper
* @createDate 2024-11-17 00:41:34
* @Entity com.fei.stock.pojo.entity.StockOuterMarketIndexInfo
*/
public interface StockOuterMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockOuterMarketIndexInfo record);

    int insertSelective(StockOuterMarketIndexInfo record);

    StockOuterMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockOuterMarketIndexInfo record);

    int updateByPrimaryKey(StockOuterMarketIndexInfo record);

    /**
     * 外盘指数行情数据查询，根据时间和大盘点数降序排序取前4
     * @return
     */
    List<OuterMarketDomain> getOuterMarketInfo();

    /**
     * 采集外盘数据
     * @param list
     * @return
     */
    int insertOutMarketData(@Param("list")List<StockOuterMarketIndexInfo> list);
}

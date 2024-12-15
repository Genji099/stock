package com.fei.stock.mapper;

import com.fei.stock.pojo.domain.StockBlockDomain;
import com.fei.stock.pojo.entity.StockBlockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author 59747
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2024-11-17 00:41:34
* @Entity com.fei.stock.pojo.entity.StockBlockRtInfo
*/
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    List<StockBlockDomain> getBlockInfo(@Param("curDate") Date curDate);

    /**
     * 插入大盘数据
     * @param stockBlockRtInfos
     * @return
     */
    int insertByList(@Param("list") List<StockBlockRtInfo> stockBlockRtInfos);
}
